package com.vrp.system.paymentsystem.paymentservice.pools;

import com.vrp.system.paymentsystem.paymentservice.models.PaymentError;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

@Component
public class PaymentErrorPool {
    private static int max_count=10000;

    private static int min_count=100;

    private volatile int queuecount=0;


    private  final  Object olock=new Object();



    private ReentrantLock lock=new ReentrantLock();

    private TimerTask task=new TimerTask() {

        @Override
       public void run() {
            List<PaymentErrorWrapper> tobeadded;
                if (queuecount % 2 == 0) {
                    receiver = tobeadded2;
                    tobeadded = tobeadded1;
                } else {
                    receiver = tobeadded1;
                    tobeadded = tobeadded2;
                }
            System.out.println("Thread Name " + Thread.currentThread().getName() + " added " + queuecount + " size " + tobeadded.size());
                Iterator<PaymentErrorWrapper> it=tobeadded.iterator();
                while(it.hasNext()){
                    PaymentErrorWrapper pw=it.next();
                    if ((System.currentTimeMillis() - pw.milliseconds)>60000L) {
                        System.out.println(System.currentTimeMillis());
                        System.out.println("amInside "+(System.currentTimeMillis() - pw.getMilliseconds()));
                        available.add(pw);
                        it.remove();
                    }
                }

                System.out.println("Thread Name " + Thread.currentThread().getName() + " added " + queuecount + " size " + tobeadded.size());
                //tobeadded.removeAll(tobedeleted);
                //tobedeleted.clear();
                System.out.println("added " + queuecount);
                queuecount = (queuecount + 1) % 2;
        }
    };
    Timer timer=new Timer();


    public int size=0;
    LinkedList<PaymentErrorWrapper> total=new LinkedList<>();
    LinkedList<PaymentErrorWrapper> available=new LinkedList<>();

    private volatile List<PaymentErrorWrapper> tobeadded1=Collections.synchronizedList(new LinkedList<>());
    private volatile List<PaymentErrorWrapper> tobeadded2=Collections.synchronizedList(new LinkedList<>());
    private volatile List<PaymentErrorWrapper> receiver=tobeadded1;

    public PaymentErrorPool(){
        IntStream.rangeClosed(1,min_count).forEach(i->this.total.add(new PaymentErrorWrapper(new PaymentError())));
        this.total.stream().forEach(available::add);
        size=this.total.size();
        timer.schedule(task, TimeUnit.SECONDS.toSeconds(1),1000L);
    }

    public PaymentErrorWrapper getPaymentErrorShell(){
        PaymentErrorWrapper paymentError=null;
        if(lock.tryLock()) {
            if (available.size() > 0) {
                paymentError=available.poll();
            } else if (size < max_count) {
                paymentError = new PaymentErrorWrapper(new PaymentError());
                total.add(paymentError);
            }
            lock.unlock();
        }
        return paymentError;
    }

    public void addPaymentError(PaymentErrorWrapper paymentError){
        paymentError.setMilliseconds(System.currentTimeMillis());
        receiver.add(paymentError);
    }


    public static int getMax_count() {
        return max_count;
    }

    public static void setMax_count(int max_count) {
        PaymentErrorPool.max_count = max_count;
    }



    static class PaymentErrorWrapper{
        private long milliseconds=0L;
        private PaymentError paymentError;

        public PaymentErrorWrapper(PaymentError paymentError){
            this.paymentError=paymentError;
        }

        public long getMilliseconds() {
            return milliseconds;
        }

        public void setMilliseconds(long milliseconds) {
            this.milliseconds = milliseconds;
        }

        public PaymentError getPaymentError() {
            return paymentError;
        }
    }
}
