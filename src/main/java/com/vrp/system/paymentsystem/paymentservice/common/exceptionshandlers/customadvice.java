package com.vrp.system.paymentsystem.paymentservice.common.exceptionshandlers;

import com.vrp.system.paymentsystem.paymentservice.common.exceptions.BadPaymentOrder;
import com.vrp.system.paymentsystem.paymentservice.models.PaymentError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class customadvice {
    @ExceptionHandler({BadPaymentOrder.class})
    public ResponseEntity<PaymentError> handlerBadPaymentOrder(RuntimeException ex , WebRequest webRequest){
                  return null;
    }
}
