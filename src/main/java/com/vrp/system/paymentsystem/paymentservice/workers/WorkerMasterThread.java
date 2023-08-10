package com.vrp.system.paymentsystem.paymentservice.workers;



import com.vrp.system.paymentsystem.paymentservice.queue.RegistrationEventQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@Component
public class WorkerMasterThread implements Runnable{
    private final int no_of_workers;
    private WorkerMasterImpl<ExecuteThreadImpl> workerMaster;
    private RegistrationEventQueue registrationEventQueue;
    private ThreadPoolImpl threadPool;
    private ApplicationContext applicationContext;
    private ExecutorService executorService;
    private Class<? extends Runnable> clazz=ExecuteThreadImpl.class;


    //@Autowired
    public WorkerMasterThread(@Autowired WorkerMasterImpl workerMaster,@Autowired RegistrationEventQueue registrationEventQueue,@Autowired ThreadPoolImpl threadPool,@Autowired ApplicationContext applicationContext){
        this.workerMaster=workerMaster;
        this.registrationEventQueue=registrationEventQueue;
        this.threadPool=threadPool;
        this.applicationContext=applicationContext;
        String str_no_of_workers=System.getProperty("no_of_workers");
        no_of_workers=(str_no_of_workers==null||"".equals(str_no_of_workers))?5:Integer.parseInt(str_no_of_workers);
        this.executorService= Executors.newFixedThreadPool(no_of_workers);
        Thread thread=new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        Thread.currentThread().setName("MasterThread");

        IntStream.range(0, no_of_workers).forEach(i -> {
            executorService.execute(applicationContext.getBean(clazz));
        });
    }


}
