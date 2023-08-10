package com.vrp.system.paymentsystem.paymentservice.workers;


import com.domain.travel.travelproject.cache.ThreadCache;
import com.domain.travel.travelproject.common.AsyncQueues;
import com.domain.travel.travelproject.externalservices.PricerService;
import com.domain.travel.travelproject.feeders.Dbfeeder;
import com.domain.travel.travelproject.joins.EnquiryJoin;
import com.domain.travel.travelproject.monitor.Monitor;
import com.domain.travel.travelproject.service.DbserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static com.domain.travel.travelproject.externalservices.PricerService.QueryAddress.GET_PACKAGES;
import static com.domain.travel.travelproject.monitor.MonitorTypes.ENRICHJOIN;

@Component
public class WorkerMasterThread implements Runnable{

   // @Autowired
    private WorkerMasterImpl<ExecuteThreadImpl> workerMaster;
    //@Autowired
    private PricerService pricerService;


    //@Autowired
    private DbserviceImpl dbservice;

    //@Autowired
    private ThreadCache<String, Package,EnquiryJoin>  threadCache;

    //@Autowired
    AsyncQueues asyncQueues;

    //@Autowired
    private Monitor monitor;

    private Dbfeeder dbfeeder;

    //@Autowired
    public WorkerMasterThread(@Autowired WorkerMasterImpl workerMaster,@Autowired PricerService pricerService,@Autowired DbserviceImpl dbservice,@Autowired ThreadCache threadCache,@Autowired AsyncQueues asyncQueues,@Autowired Monitor monitor,@Autowired Dbfeeder dbfeeder){
        this.workerMaster=workerMaster;
        this.pricerService=pricerService;
        this.dbservice=dbservice;
        this.threadCache=threadCache;
        this.asyncQueues=asyncQueues;
        this.monitor=monitor;
        this.dbfeeder=dbfeeder;
        Thread thread=new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        Thread.currentThread().setName("MasterThread");
        asyncQueues.populateMapQueues();
        HttpRequest request= pricerService.getHttpRequest(Collections.EMPTY_MAP,GET_PACKAGES, HttpRequest.BodyPublishers.noBody(),"");
        threadCache.setClazz(Package.class);
        getSerializedEnquiryJoin();
        monitor.getMonitorCache().store(ENRICHJOIN,threadCache.getCache());
        threadCache.setSetter((joinObject,_package)->{joinObject.setPackage(_package);});
        threadCache.start(request,(Package _package)->_package.getQueryId());
        workerMaster.setClass(ExecuteThreadImpl.class);
        new Thread(dbfeeder).start();
        ;
        System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Initialized MasterWorker Thread");
        while(true){
            System.out.println(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Scanning for System Load");
            workerMaster.ScanloadofApp();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getSerializedEnquiryJoin(){
        List<EnquiryJoin> enquiryJoins=  dbservice.assembleEnquiryJoin();
        ConcurrentMap map=enquiryJoins.stream().collect(Collectors.toConcurrentMap(EnquiryJoin::getQueryId, join->(EnquiryJoin)join));
        threadCache.setCache(new ConcurrentHashMap<>(map));
    }
}
