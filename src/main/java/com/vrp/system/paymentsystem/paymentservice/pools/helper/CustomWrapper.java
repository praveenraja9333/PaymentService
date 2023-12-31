package com.vrp.system.paymentsystem.paymentservice.pools.helper;

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

    public T getData() {
        return data;
    }
}
