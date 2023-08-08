package com.vrp.system.paymentsystem.paymentservice.common.exceptions;

public class BadPaymentOrder extends RuntimeException{
    public BadPaymentOrder(String message){
        super(message);
    }
}
