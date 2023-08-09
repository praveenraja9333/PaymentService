package com.vrp.system.paymentsystem.paymentservice.api;

import com.vrp.system.paymentsystem.paymentservice.common.exceptions.BadPaymentOrder;
import com.vrp.system.paymentsystem.paymentservice.models.Order;
import com.vrp.system.paymentsystem.paymentservice.service.PaymentApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentApi {

    @Autowired
    private PaymentApiService paymentApiService;

    @PostMapping("/api/v1/payment")
    public void postPayment(@Validated @RequestBody Order order){
            paymentApiService.paymentOrderSave(order);
    }
    
}
