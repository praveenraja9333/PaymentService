package com.vrp.system.paymentsystem.paymentservice.queue;

import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class RegistrationEventQueue {
    private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ssZ");
    private PriorityQueue<RegistrationEvent> queue=new PriorityQueue<>((re,re1)->
           Long.compare(ZonedDateTime.parse(re1.getDatatime()).toEpochSecond(),ZonedDateTime.parse(re.getDatatime()).toEpochSecond())
    );
    private Map<RegistrationEvent,RegistrationEvent> map=new HashMap<>();
}
