package com.vrp.system.paymentsystem.paymentservice.workers;

import java.util.concurrent.ExecutorService;

public interface ThreadPool{
    public ExecutorService populateThreadpool(int noThreads);
}

