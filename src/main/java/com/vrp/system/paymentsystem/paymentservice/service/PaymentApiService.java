package com.vrp.system.paymentsystem.paymentservice.service;


import com.vrp.system.paymentsystem.paymentservice.common.exceptions.BadPaymentOrder;
import com.vrp.system.paymentsystem.paymentservice.common.exceptions.PaymentEventRegistrationException;
import com.vrp.system.paymentsystem.paymentservice.dao.OrderDao;
import com.vrp.system.paymentsystem.paymentservice.dao.PaymentOrderDao;
import com.vrp.system.paymentsystem.paymentservice.models.Order;
import com.vrp.system.paymentsystem.paymentservice.models.PaymentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentApiService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PaymentOrderDao paymentOrderDao;
    public void paymentOrderSave(Order order){
                    List<PaymentOrder> paymentOrderList=order.getPaymentOrderList();
                    PaymentOrder paymentorder=null;
                    Order order1=null;

                    for(PaymentOrder paymentOrder:paymentOrderList){
                        paymentOrder.setOrder(order);

                    }
                    if((order1=orderDao.save(order))==null){
                    throw new PaymentEventRegistrationException("[Error] Failed Payment:- order Event "+order.getCheckoutid()+" Failed to be stored");
                    }


    }

}
