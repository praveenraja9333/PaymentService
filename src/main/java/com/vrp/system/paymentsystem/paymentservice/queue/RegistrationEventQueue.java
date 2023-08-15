package com.vrp.system.paymentsystem.paymentservice.queue;

import com.vrp.system.paymentsystem.paymentservice.dao.RegistrationEventDao;
import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;
import com.vrp.system.paymentsystem.paymentservice.reactiveflow.Publisher;
import com.vrp.system.paymentsystem.paymentservice.reactiveflow.Subscriber;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public final class RegistrationEventQueue {
    private static Logger LOG= LogManager.getLogger(RegistrationEventQueue.class);
    private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ssZ");
    private PriorityQueue<RegistrationEvent> queue=new PriorityQueue<>((re,re1)->
           Long.compare(ZonedDateTime.parse(re1.getDatatime()).toEpochSecond(),ZonedDateTime.parse(re.getDatatime()).toEpochSecond())
    );
    private volatile LinkedList<RegistrationEvent> deadLetterQueue=new LinkedList<>();
    private final  Map<RegistrationEvent,RegistrationEvent> map=new ConcurrentHashMap<>();
    private final  Map<RegistrationEvent,Integer> retriesCache=new ConcurrentHashMap<>();
    private final Set<Subscriber<RegistrationEvent>> subscribers=new HashSet<>();
    @Autowired
    private  RegistrationEventDao registrationEventDao;
    @PostConstruct
    public void init(){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()-> {
            synchronized (this) {
                LOG.info("Event started ++++++++++++++++++++++++ " + deadLetterQueue.size());
                while (!deadLetterQueue.isEmpty()) {
                    LOG.info("inside loop Event started ++++++++++++++++++++++++ " + deadLetterQueue.size());
                    LOG.info(registrationEventDao.save(deadLetterQueue.poll()));
                }
            }
        },1,10, TimeUnit.SECONDS);
    }

    private final Publisher<RegistrationEvent> registrationEventPublisher=new Publisher<RegistrationEvent>() {
        @Override
        public void publish(RegistrationEvent registrationEvent) {
                for(Subscriber<RegistrationEvent> subscriber: subscribers){
                    subscriber.onPublish(registrationEvent);
                }
        }
        @Override
        public void delete(RegistrationEvent registrationEvent) {
                for(Subscriber<RegistrationEvent> subscriber:subscribers){
                    subscriber.onDelete(registrationEvent);
                }
        }

        @Override
        public void updated(RegistrationEvent registrationEvent) {
               for(Subscriber<RegistrationEvent> subscriber:subscribers){
                     subscriber.onUpdate(registrationEvent);
               }
        }

        @Override
        public void error(RegistrationEvent registrationEvent) {
                for(Subscriber<RegistrationEvent> subscriber:subscribers){
                    subscriber.onError(registrationEvent);
                }
        }
    };

    private Subscriber<RegistrationEvent> registrationEventSubscribers=new Subscriber<RegistrationEvent>() {
        @Override
        public void onPublish(RegistrationEvent registrationEvent) {
            map.put(registrationEvent,registrationEvent);
            queue.add(registrationEvent);
            registrationEventPublisher.publish(registrationEvent);
        }

        @Override
        public void onDelete(RegistrationEvent registrationEvent) {
            map.remove(registrationEvent);
            retriesCache.remove(registrationEvent);
            addDeadPayments(registrationEvent);
            registrationEventPublisher.delete(registrationEvent);
        }

        @Override
        public void onUpdate(RegistrationEvent registrationEvent) {
            map.put(registrationEvent,registrationEvent);
            registrationEventPublisher.updated(registrationEvent);
        }

        @Override
        public void onError(RegistrationEvent registrationEvent) {
            retriesCache.merge(registrationEvent,1,Integer::sum);
            registrationEventPublisher.error(registrationEvent);
        }
    };

    public Subscriber<RegistrationEvent> getRegistrationEventSubscriber() {
        return registrationEventSubscribers;
    }


    public boolean addSubcribers(Subscriber<RegistrationEvent> subscriber){
             return this.subscribers.add(subscriber);
    }

    public int getNoOfretries(RegistrationEvent re){
        return retriesCache.containsKey(re)?retriesCache.get(re):0;
    }

    public synchronized boolean add(RegistrationEvent registrationEvent){
            return queue.add(registrationEvent);
    }
    public synchronized RegistrationEvent  poll(){
            return queue.poll();
    }
    public synchronized  boolean remove(RegistrationEvent registrationEvent){
        return queue.remove(registrationEvent);
    }

    public synchronized int queueSize(){
        return queue.size();
    }

    public synchronized boolean addDeadPayments(RegistrationEvent re){
        return deadLetterQueue.add(re);
    }

    public Integer getDeadLetterQueueSize(){
        return deadLetterQueue.size();
    }
}
