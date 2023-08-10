package com.vrp.system.paymentsystem.paymentservice.common.exceptionshandlers;

import com.vrp.system.paymentsystem.paymentservice.common.exceptions.BadPaymentOrder;
import com.vrp.system.paymentsystem.paymentservice.models.PaymentError;
import com.vrp.system.paymentsystem.paymentservice.pools.helper.CustomWrapper;
import com.vrp.system.paymentsystem.paymentservice.pools.PaymentErrorPool;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class customadvice {

    private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ssZ");
    @Autowired
    private PaymentErrorPool paymentErrorPool;
    @ExceptionHandler({BadPaymentOrder.class, UnexpectedTypeException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<PaymentError> handlerBadPaymentOrder(RuntimeException ex , WebRequest webRequest){
                  CustomWrapper<PaymentError> cwp =paymentErrorPool.getShell();
                  PaymentError pe=cwp.getData();
                  pe.setStatus(HttpStatus.BAD_REQUEST.toString());
                  pe.setReason(ex.getMessage());
                  pe.setTimestamp(ZonedDateTime.now().format(dateTimeFormatter));
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pe);
    }
}
