package com.vrp.system.paymentsystem.paymentservice.reactiveflow;

public abstract class AbstractSubscriberImpl<T> implements Subscriber<T> {
    @Override
    public void onPublish(T t) {
            throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }

    @Override
    public void onDelete(T t) {
        throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }

    @Override
    public void onUpdate(T t) {
        throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }

    @Override
    public void onError(T t) {
        throw new UnsupportedOperationException(" Method cannot called directly, Please override");
    }
}
