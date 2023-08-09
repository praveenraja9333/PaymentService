package com.vrp.system.paymentsystem.paymentservice.reactiveflow;

public interface Publisher<T> {
    void publish(T t);
    void delete(T t);
    void updated(T t);
}
