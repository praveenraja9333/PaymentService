package com.vrp.system.paymentsystem.paymentservice.pools;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CustomeTimerTask<T> extends TimerTask {
    AbstractErrorHelperPool abs;

    private volatile int queuecount=0;

    public CustomeTimerTask(AbstractErrorHelperPool<T> abs){
              this.abs=abs;
    }

    @Override
    public void run() {
        List<CustomWrapper<T>> tobeadded;
        if (queuecount % 2 == 0) {
            abs.receiver = abs.tobeadded2;
            tobeadded =abs.tobeadded1;
        } else {
            abs.receiver = abs.tobeadded1;
            tobeadded = abs.tobeadded2;
        }
        System.out.println("Thread Name " + Thread.currentThread().getName() + " added " + queuecount + " size " + tobeadded.size());
        Iterator<CustomWrapper<T>> it=tobeadded.iterator();
        while(it.hasNext()){
             CustomWrapper<T> pw=it.next();
            if ((System.currentTimeMillis() - pw.getMilliseconds())>60000L) {
                System.out.println(System.currentTimeMillis());
                System.out.println("amInside "+(System.currentTimeMillis() - pw.getMilliseconds()));
                try {
                    if(abs.lock.tryLock(2, TimeUnit.SECONDS)) {
                        abs.available.add(pw);
                        it.remove();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Thread Name " + Thread.currentThread().getName() + " added " + queuecount + " size " + tobeadded.size());
        //tobeadded.removeAll(tobedeleted);
        //tobedeleted.clear();
        System.out.println("added " + queuecount);
        queuecount = (queuecount + 1) % 2;
    }
}
