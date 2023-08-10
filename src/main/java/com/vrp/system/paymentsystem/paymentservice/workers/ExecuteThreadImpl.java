package com.vrp.system.paymentsystem.paymentservice.workers;


import com.domain.travel.travelproject.dao.DaoStores;
import com.domain.travel.travelproject.joins.EnquiryJoin;
import com.domain.travel.travelproject.models.Query;
import com.domain.travel.travelproject.monitor.Monitor;
import com.domain.travel.travelproject.service.Dbservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.domain.travel.travelproject.common.AsyncQueues.QueueNames.FAILED_QUERY_QUEUE;
import static com.domain.travel.travelproject.common.AsyncQueues.QueueNames.QUERY_QUEUE;
import static com.domain.travel.travelproject.monitor.MonitorTypes.ENRICHJOIN;


public class ExecuteThreadImpl implements  Runnable {


    @Autowired
   private DaoStores daoStores;

    @Autowired
    private Monitor monitor;

    @Autowired
    private Dbservice dbservice;

    @Override
    public void run() {

        while(true) {
            if(WorkerMasterImpl.isExitThread()){
                System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"  Thread Exit intended. Hence Exiting");
                break;
            }
            System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"  Tracing the queue");
            Query query=(Query)daoStores.getQueue(QUERY_QUEUE).poll();
            if(query==null) {
                System.out.println(Thread.currentThread().getThreadGroup() + ":==:" + Thread.currentThread().getName() + "  No Queue initialized yet");

            }
            else {
                System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"Queue initialized");
                System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"Query Being processed is "+query.getQueryid()+"  "+query.getFirstname()+"  "+query.getLastname());
                //pushing the query logic here
                ObjectMapper objectMapper=new ObjectMapper();
                String jsonstr="";
                try {
                    jsonstr=objectMapper.writeValueAsString(query);
                    System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Query json be produced"+ jsonstr);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:9091/v1/api/postcity"))
                        .header("Content-type", "application/json")
                        .method("POST", HttpRequest.BodyPublishers.ofString(jsonstr))
                        .build();
                HttpResponse<String> response = null;
                try {
                    response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                }catch (IOException  | InterruptedException e) {
                    e.printStackTrace();

                }finally {
                    if (response!=null&&"0".equals(response.body())) {
                        System.out.println(Thread.currentThread().getThreadGroup() + ":==:" + Thread.currentThread().getName() + " query id " + query.getQueryid() + " successfully pushed to Mailer");
                    } else {
                        if(daoStores.registerFailure(query.getQueryid())){
                            daoStores.getQueue(QUERY_QUEUE).push(query);
                        }else{
                            System.out.println(Thread.currentThread().getThreadGroup() + ":==:" + Thread.currentThread().getName() + " Retries limit exceeded query id " + query.getQueryid() + " Adding to the failed queue");
                            daoStores.getQueue(FAILED_QUERY_QUEUE).add(query);
                        }

                        System.out.println(Thread.currentThread().getThreadGroup() + ":==:" + Thread.currentThread().getName() + " query id " + query.getQueryid() + " Failed to be pushed to Mailer");
                    }

                }

                //End Push Query Logic
                //database insertion query
                if(dbservice.serializeQuery(query)==0){
                    //log failure
                    System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" query id "+query.getQueryid()+"Query data serialization failure");
                }
                EnquiryJoin enquiryJoin=new EnquiryJoin();
                enquiryJoin.setQuery(query);
                enquiryJoin.setQueryId(query.getQueryid());
                System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Enrich join called"+enquiryJoin.getQueryId()+"enrich join");
                monitor.getMonitorCache().get(ENRICHJOIN).put(query.getQueryid(),enquiryJoin);

                // retrive the priced details

                //


            }
            if (query != null){
                //logic hear
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
