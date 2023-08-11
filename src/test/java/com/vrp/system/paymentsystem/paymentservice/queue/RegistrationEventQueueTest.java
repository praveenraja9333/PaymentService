package com.vrp.system.paymentsystem.paymentservice.queue;

import com.vrp.system.paymentsystem.paymentservice.dao.RegistrationEventDao;
import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;
import org.awaitility.Awaitility;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@SpringBootTest
class RegistrationEventQueueTest {
    @Mock
    private RegistrationEventDao dao;

    @InjectMocks
    private static RegistrationEventQueue registrationEventQueue;


    @BeforeAll
    static void setUp(){
        registrationEventQueue=new RegistrationEventQueue();
    }

    @Test
    void addDeadPayments(){
        ArgumentCaptor<RegistrationEvent> argumentCaptor=ArgumentCaptor.forClass(RegistrationEvent.class);
        for(int i=0;i<1000;i++) {
            registrationEventQueue.addDeadPayments(RegistrationEvent.newBuilder().setCheckoutid(UUID.randomUUID()).build());
        }
        await().pollDelay(1,TimeUnit.SECONDS).
                until(registrationEventQueue::getDeadLetterQueueSize, CoreMatchers.is(0));
        Mockito.verify(dao,times(1000)).save(argumentCaptor.capture());
        
    }

    @Test
    void mockTesting(){
        List<String> l= Mockito.mock(LinkedList.class);
        l.add("Hello");
        ArgumentCaptor<String> s=ArgumentCaptor.forClass(String.class);
        Mockito.verify(l).add(s.capture());
        assertEquals("Hello",s.getValue());
        l.add("Bye");
        s=ArgumentCaptor.forClass(String.class);
        Mockito.verify(l,times(2)).add(s.capture());
        assertEquals("Bye",s.getValue());
        Mockito.when(l.size()).thenReturn(2);
        assertEquals(2,l.size());
    }

}