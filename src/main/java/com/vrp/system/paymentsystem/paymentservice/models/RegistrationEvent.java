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
    private UUID checkoutid;
    private String currencycode;
    private String datetime;
    private String amount;
    private Status status;

    private RegistrationEvent(){}

    public UUID getCheckoutid() {
        return checkoutid;
    }

    private void setCheckoutid(UUID checkoutid) {
        if(this.checkoutid==null)
        this.checkoutid = checkoutid;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    private void setCurrencycode(String currencycode) {
        if(this.currencycode==null)
        this.currencycode = currencycode;
    }

    public String getDatatime() {
        return datetime;
    }

    private void setDatetime(String datatime) {
        if(this.datetime==null)
            this.datetime = datatime;
    }

    public String getAmount() {
        return amount;
    }

    private void setAmount(String amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

   public static class RegistrationEventBuilder extends Builder<RegistrationEvent>{
       private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ssZ");
       private static SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy hh:mm:ssZ");
       private UUID checkoutid;
       private String currencycode;
       private String datetime;
       private String amount;
       private Status status;

       public RegistrationEventBuilder setCheckoutid(UUID checkoutid) {
           this.checkoutid = checkoutid;
           return self();
       }
       public RegistrationEventBuilder setCurrencycode(String currencycode){
           this.currencycode=currencycode;
           return self();
       }
       public RegistrationEventBuilder  setAmount(String amount){
             this.amount=amount;
             return self();
       }
       private RegistrationEventBuilder setDatetime(String datatime) {
           if(this.datetime==null)
               this.datetime = datatime;
           return self();
       }
       public RegistrationEventBuilder setDatetime(Date date){
           return setDatetime(format.format(date));
       }
       public RegistrationEventBuilder setDatetime(ZonedDateTime zonedDateTime){
            return setDatetime(zonedDateTime.format(dateTimeFormatter));
       }
       public RegistrationEventBuilder setStatus(Status status) {
           this.status = status;
           return self();
       }
       public RegistrationEventBuilder self(){
           return this;
       }

       @Override
       public RegistrationEvent build() {
           RegistrationEvent re=new RegistrationEvent();
           re.setCheckoutid(this.checkoutid);
           re.setCurrencycode(this.currencycode);
           re.setDatetime(this.datetime);
           re.setAmount(this.amount);
           re.setStatus(this.status);
           return re;
       }
   }

   public static RegistrationEventBuilder newBuilder(){
        return new RegistrationEventBuilder();
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationEvent that = (RegistrationEvent) o;

        if (!checkoutid.equals(that.checkoutid)) return false;
        if (!currencycode.equals(that.currencycode)) return false;
        if (!datetime.equals(that.datetime)) return false;
        return amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        int result = checkoutid.hashCode();
        result = 31 * result + currencycode.hashCode();
        result = 31 * result + datetime.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }
}
