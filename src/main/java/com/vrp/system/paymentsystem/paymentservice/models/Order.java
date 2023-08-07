package com.vrp.system.paymentsystem.paymentservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

//@Transactional
//@Entity
//@Table(name="Orders")
public class Order {
    @Id
    private  UUID checkoutid;
    private String buyerinfo;
    private String currencycode;
    private boolean status;
    List<PaymentOrder> paymentOrderList;

    public UUID getCheckoutid() {
        return checkoutid;
    }

    public void setCheckoutid(UUID checkoutid) {
        if(this.checkoutid==null)
        this.checkoutid = checkoutid;
    }

    public String getBuyerinfo() {
        return buyerinfo;
    }

    public void setBuyerinfo(String buyerinfo) {
        if(this.buyerinfo==null)
        this.buyerinfo = buyerinfo;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        if(this.currencycode==null)
        this.currencycode = currencycode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<PaymentOrder> getPaymentOrderList() {
        return paymentOrderList;
    }

    public void setPaymentOrderList(List<PaymentOrder> paymentOrderList) {
        if(this.paymentOrderList==null)
        this.paymentOrderList = paymentOrderList;
    }
}
