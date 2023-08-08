package com.vrp.system.paymentsystem.paymentservice.models;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class PaymentError {

    private String timestamp;

    private String status;
    private String reason;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
