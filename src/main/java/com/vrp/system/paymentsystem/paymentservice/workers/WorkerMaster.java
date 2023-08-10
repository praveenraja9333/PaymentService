package com.vrp.system.paymentsystem.paymentservice.workers;

public interface WorkerMaster<T extends Runnable > {
    public void scanLoadoFApp();
    public void scanTheSystem();
    public void setClass(Class<T> t);
    public void scaleTheSystem(int noThreads);
}
