package com.vrp.system.paymentsystem.paymentservice.models;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class RegistrationEvent {
    private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ssZ");
    private static SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy hh:mm:ssZ");
    private UUID checkoutid;
    private String currencycode;
    private String datetime;
    private String amount;
    private Status status;

    public UUID getCheckoutid() {
        return checkoutid;
    }

    public void setCheckoutid(UUID checkoutid) {
        if(this.checkoutid==null)
        this.checkoutid = checkoutid;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        if(this.currencycode==null)
        this.currencycode = currencycode;
    }

    public String getDatatime() {
        return datetime;
    }

    public void setDatetime(String datatime) {
        if(this.datetime==null)
            this.datetime = datatime;
    }
    public void setDatetime(Date date){
        setDatetime(format.format(date));
    }
    public void setDatetime(ZonedDateTime zonedDateTime){
        setDatetime(zonedDateTime.format(dateTimeFormatter));
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
