package com.vrp.system.paymentsystem.paymentservice.reactiveflow;

public interface Subscriber<T> {
    void onPublish(T t);
    void onDelete(T t);
    void onUpdate(T t);
    void onError(T t);
}
