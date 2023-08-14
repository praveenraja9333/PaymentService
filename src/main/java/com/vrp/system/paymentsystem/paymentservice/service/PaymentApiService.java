package com.vrp.system.paymentsystem.paymentservice.service;

;
import com.vrp.system.paymentsystem.paymentservice.common.exceptions.PaymentEventRegistrationException;
import com.vrp.system.paymentsystem.paymentservice.dao.OrderDao;
import com.vrp.system.paymentsystem.paymentservice.dao.PaymentOrderDao;
import com.vrp.system.paymentsystem.paymentservice.models.Order;
import com.vrp.system.paymentsystem.paymentservice.models.PaymentOrder;
import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;
import com.vrp.system.paymentsystem.paymentservice.models.Status;
import com.vrp.system.paymentsystem.paymentservice.reactiveflow.AbstractPublisherImpl;
import com.vrp.system.paymentsystem.paymentservice.reactiveflow.Publisher;
import com.vrp.system.paymentsystem.paymentservice.reactiveflow.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PaymentApiService {
    private static final Pattern REFEXURL= Pattern.compile("http[s]?://[.]*");
    private static final String TESTHOST="http://localhost:8080/";
    private static final String WEBHOOKURL=getWebhookurl();
    private NumberFormat numberFormat=NumberFormat.getNumberInstance();
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PaymentOrderDao paymentOrderDao;
    private Publisher<RegistrationEvent> publisher=new AbstractPublisherImpl<RegistrationEvent>(){
                     @Override
                     public void publish(RegistrationEvent re){
                              for(Subscriber<RegistrationEvent> subscriber:registrationSubscribers){
                                   subscriber.onPublish(re);
                                }
                        }
    };
    private Set<Subscriber<RegistrationEvent>> registrationSubscribers=new HashSet<>();
    public void paymentOrderSave(Order order){
                    List<PaymentOrder> paymentOrderList=order.getPaymentOrderList();
                    Order order1=null;
                    Currency currency =Currency.getInstance(order.getCurrencycode());
                    numberFormat.setCurrency(currency);
                    double amount=0.0;
                    for(PaymentOrder paymentOrder:paymentOrderList){
                        paymentOrder.setOrder(order);
                        try {
                            amount +=numberFormat.parse(paymentOrder.getAmount()).doubleValue();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    if((order1=orderDao.save(order))==null){
                    throw new PaymentEventRegistrationException("[Error] Failed Payment:- order Event "+order.getCheckoutid()+" Failed to be stored");
                    }


                    RegistrationEvent registrationEvent=RegistrationEvent.newBuilder().setCheckoutid(order.getCheckoutid()).setCurrencycode(order.getCurrencycode()).setAmount(numberFormat.format(amount))
                            .setDatetime(ZonedDateTime.now()).setStatus(Status.PENDING).build();
                    publisher.publish(registrationEvent);
    }

    public boolean addSubsribers(Subscriber<RegistrationEvent> subscriber){
              return  registrationSubscribers.add(subscriber);
    }

    public static String getWebhookurl(){
        String rawUrl=System.getProperty("webHookUrl");
        rawUrl=(rawUrl==null)?TESTHOST:REFEXURL.matcher(rawUrl).matches()?rawUrl:TESTHOST;
        return rawUrl;
    }
    
}
