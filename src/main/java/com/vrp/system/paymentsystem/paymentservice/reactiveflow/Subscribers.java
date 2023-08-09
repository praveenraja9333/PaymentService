package com.vrp.system.paymentsystem.paymentservice.reactiveflow;

public interface Subscribers<T> {
    void onPublish(T t);
    void onDelete(T t);
    void onUpdate(T t);
}
