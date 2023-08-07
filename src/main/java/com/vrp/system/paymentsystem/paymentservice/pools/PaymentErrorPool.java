package com.vrp.system.paymentsystem.paymentservice.pools;

import com.vrp.system.paymentsystem.paymentservice.models.PaymentError;

import java.util.List;

public class PaymentErrorPool {
    private static int max_count=10000;
    List<PaymentError> total;
    List<PaymentError> available;

    public static int getMax_count() {
        return max_count;
    }

    public static void setMax_count(int max_count) {
        PaymentErrorPool.max_count = max_count;
    }

    public List<PaymentError> getTotal() {
        return total;
    }

    public void setTotal(List<PaymentError> total) {
        this.total = total;
    }

    public List<PaymentError> getAvailable() {
        return available;
    }

    public void setAvailable(List<PaymentError> available) {
        this.available = available;
    }
}
