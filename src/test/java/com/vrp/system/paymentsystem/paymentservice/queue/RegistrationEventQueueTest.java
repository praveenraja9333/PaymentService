package com.vrp.system.paymentsystem.paymentservice.queue;

import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class RegistrationEventQueueTest {

    private static RegistrationEventQueue registrationEventQueue;


    @BeforeAll
    static void setUp(){
        registrationEventQueue=new RegistrationEventQueue();
    }

    @Test
    void addDeadPayments(){
        for(int i=0;i<1000;i++) {
            registrationEventQueue.addDeadPayments(RegistrationEvent.newBuilder().setCheckoutid(UUID.randomUUID()).build());
        }
    }

}