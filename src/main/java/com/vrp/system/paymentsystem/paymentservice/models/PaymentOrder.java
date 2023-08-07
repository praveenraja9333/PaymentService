package com.vrp.system.paymentsystem.paymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

import java.util.UUID;

//@Entity
//@Transactional
//@Table(name="Payment_Orders")
public class PaymentOrder {
    private String sellername;
    private String buyername;
    private String amount;
    private String currencycode;
    private boolean ledgerUpdated;
    private boolean walletUpdated;
    private UUID checkoutid;

    private boolean initamount;

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {

        if(this.sellername==null)
        this.sellername = sellername;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        if(this.buyername==null)
        this.buyername = buyername;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        if(!this.initamount) {
            this.amount = amount;
            this.initamount=!this.initamount;
        }
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        if(this.currencycode==null)
        this.currencycode = currencycode;
    }

    public boolean isLedgerUpdated() {
        return ledgerUpdated;
    }

    public void setLedgerUpdated(boolean ledgerUpdated) {
        this.ledgerUpdated = ledgerUpdated;
    }

    public boolean isWalletUpdated() {
        return walletUpdated;
    }

    public void setWalletUpdated(boolean walletUpdated) {
        this.walletUpdated = walletUpdated;
    }

    public UUID getUUID() {
        return this.checkoutid;
    }

    public void setUUID(UUID UUID) {
        if(this.checkoutid==UUID)
            this.checkoutid = UUID;
    }
}
