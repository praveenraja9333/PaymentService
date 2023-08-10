package com.vrp.system.paymentsystem.paymentservice.reactiveflow;

public abstract class AbstractPublisherImpl<T> implements Publisher<T> {
    @Override
    public void publish(T t) {
        throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }

    @Override
    public void delete(T t) {
        throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }

    @Override
    public void updated(T t) {
        throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }

    @Override
    public void error(T t) {
        throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }
}
