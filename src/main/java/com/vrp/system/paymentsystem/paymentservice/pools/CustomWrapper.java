package com.vrp.system.paymentsystem.paymentservice.pools;

import com.vrp.system.paymentsystem.paymentservice.models.PaymentError;

public class CustomWrapper<T> {

    private long milliseconds=0L;
    private T data;

    public CustomWrapper(T data){
        this.data=data;
    }

    protected long getMilliseconds() {
        return milliseconds;
    }

    protected void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    protected T getData() {
        return data;
    }
}
