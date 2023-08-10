package com.vrp.system.paymentsystem.paymentservice.workers;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;
import com.vrp.system.paymentsystem.paymentservice.queue.RegistrationEventQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ExecuteThreadImpl implements  Runnable {

    private static Logger LOG= LogManager.getLogger(ExecuteThreadImpl.class);

    @Autowired
   private RegistrationEventQueue registrationEventQueue;


    @Override
    public void run() {

        while(true) {
            if(WorkerMasterImpl.isExitThread()){
                LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"  Thread Exit intended. Hence Exiting");
                break;
            }
            LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"  Tracing the queue");
            RegistrationEvent registrationEvent=registrationEventQueue.poll();
            if(registrationEvent==null) {
                LOG.info(Thread.currentThread().getThreadGroup() + ":==:" + Thread.currentThread().getName() + "  No Queue initialized yet");

            }
            else {
                LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"Queue initialized");
                LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"Query Being processed is "+query.getQueryid()+"  "+query.getFirstname()+"  "+query.getLastname());
                
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
