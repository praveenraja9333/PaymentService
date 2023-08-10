package com.vrp.system.paymentsystem.paymentservice.pools;

import com.vrp.system.paymentsystem.paymentservice.models.PaymentError;
import com.vrp.system.paymentsystem.paymentservice.pools.helper.AbstractErrorHelperPool;
import com.vrp.system.paymentsystem.paymentservice.pools.helper.CustomWrapper;
import com.vrp.system.paymentsystem.paymentservice.pools.helper.CustomeTimerTask;
import org.springframework.stereotype.Component;

import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.stream.IntStream;


@Component
public final class PaymentErrorPool extends AbstractErrorHelperPool<PaymentError> {
    private TimerTask task;
    private ScheduledExecutorService executor= Executors.newScheduledThreadPool(1);
    public PaymentErrorPool(){
        IntStream.rangeClosed(1,min_count).forEach(i->total.add(new CustomWrapper<>(new PaymentError())));
        total.stream().forEach(available::add);
        size= total.size();
    }

    public void init(){
          CustomeTimerTask<PaymentError> cwptask=new CustomeTimerTask<>(this);
          executor.scheduleAtFixedRate(cwptask,1,1,TimeUnit.SECONDS);
    }
    @Override
    public CustomWrapper<PaymentError> getShell() {
        CustomWrapper<PaymentError> cwp=null;
        if(lock.tryLock()){
            if(available.size()>1){
                cwp=available.poll();
            }else{
                if(size<max_count){
                  cwp=new CustomWrapper<>(new PaymentError());
                  total.add(cwp);
                }
            }
            lock.unlock();
        }
        return cwp;
    }
}
