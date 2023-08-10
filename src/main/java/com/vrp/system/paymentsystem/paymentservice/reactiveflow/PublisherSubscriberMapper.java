package com.vrp.system.paymentsystem.paymentservice.reactiveflow;

import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;
import com.vrp.system.paymentsystem.paymentservice.queue.RegistrationEventQueue;
import com.vrp.system.paymentsystem.paymentservice.service.PaymentApiService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublisherSubscriberMapper {

    @Autowired
    private RegistrationEventQueue registrationEventQueue;
    @Autowired
    private PaymentApiService paymentApiService;

    @PostConstruct
    public void paymentToRegistrationEvent(){
               paymentApiService.addSubsribers(registrationEventQueue.getRegistrationEventSubscriber());
    }
}
