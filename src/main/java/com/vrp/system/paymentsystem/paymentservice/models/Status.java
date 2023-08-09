package com.vrp.system.paymentsystem.paymentservice.models;

public enum Status {
    PENDING(1), PROCESSED(2), COMPLETED(3), SUCCESS(4), FAILED(5);
    private Integer id;

    Status(Integer integer) {
        this.id = id;
    }
    public Integer getId(){
        return id;
    }

}
