package com.vrp.system.paymentsystem.paymentservice.api;

import com.vrp.system.paymentsystem.paymentservice.common.exceptions.BadPaymentOrder;
import com.vrp.system.paymentsystem.paymentservice.models.Order;
import com.vrp.system.paymentsystem.paymentservice.service.PaymentApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentApi {

    @Autowired
    private PaymentApiService paymentApiService;

    @PostMapping("/api/v1/payment")
    public int postPayment(@Validated @RequestBody Order order){
            paymentApiService.paymentOrderSave(order);
            return 0;
    }

    @GetMapping("/api/v1/payment")
    public void getPayment(){
        throw new RuntimeException("Get mapping is supported");
    }
    @DeleteMapping("/api/v1/payment")
    public void deletePayment(){
        throw new RuntimeException("delete mapping is supported");
    }

    @PutMapping("/api/v1/payment")
    public void putPayment(){
        throw new RuntimeException("put mapping is supported");
    }
    
}
