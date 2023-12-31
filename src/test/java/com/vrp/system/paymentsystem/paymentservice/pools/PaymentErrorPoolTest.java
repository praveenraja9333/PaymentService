package com.vrp.system.paymentsystem.paymentservice.pools;

import com.vrp.system.paymentsystem.paymentservice.models.PaymentError;
import com.vrp.system.paymentsystem.paymentservice.pools.helper.CustomWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentErrorPoolTest {

    public static PaymentErrorPool paymentErrorPool;
    @BeforeAll
    static void setUp(){
        paymentErrorPool=new PaymentErrorPool();
    }

    @Test
    void max_count(){
        assertEquals(10000,paymentErrorPool.getMax_count());
    }

    @Test
    void getPaymentErrorShell(){
        //Object O=
        assertTrue(paymentErrorPool.getShell() instanceof CustomWrapper<PaymentError>);
    }

    @Test
    void addPaymentError(){

        Runnable r=()-> {
            int counter=0;
            while (counter++ < 100000) {
                paymentErrorPool.addPaymentError(new CustomWrapper<>(new PaymentError()));
            }
        };
        Thread t1=new Thread(r);
        Thread t2=new Thread(r);
        t1.setName("t1");t2.setName("t2");
        t1.start();t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}