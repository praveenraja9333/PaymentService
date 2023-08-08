package com.vrp.system.paymentsystem.paymentservice.api;

import com.vrp.system.paymentsystem.paymentservice.common.exceptions.BadPaymentOrder;
import com.vrp.system.paymentsystem.paymentservice.models.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentApi {

    @PostMapping("/api/v1/payment")
    public int postPayment(@RequestBody Order order){
            Order order1= order;
              return 0;
    }
    
}
