package com.vrp.system.paymentsystem.paymentservice.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentApiServiceTest {

    @Test
    public void getWebhookurl(){
        String myTestUrl="https://myTestUrl:8080/";
        System.setProperty("webHookUrl",myTestUrl);
        assertEquals(myTestUrl,PaymentApiService.getWebhookurl());
    }

}