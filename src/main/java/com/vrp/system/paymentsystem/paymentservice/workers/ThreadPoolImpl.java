package com.vrp.system.paymentsystem.paymentservice.workers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadPoolImpl implements ThreadPool {
    @Autowired
    public Systemresourcetothread systhread;
    @Override
    public ExecutorService populateThreadpool(int noofThreads) {
        if(noofThreads<systhread.calculateThreads()) {
            System.out.println("Number of threads finalized "+noofThreads);
            return Executors.newFixedThreadPool(noofThreads);
        }
        System.out.println("Number of threads finalized "+noofThreads);
        return Executors.newFixedThreadPool(systhread.calculateThreads());
    }

}
