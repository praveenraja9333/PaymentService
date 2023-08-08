package com.vrp.system.paymentsystem.paymentservice.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Transactional
@Table(name="Payment_Orders")
public class PaymentOrder {
    @Id
    @GeneratedValue
    private String id;
    @Column
    @NotNull(message = "sellername should not be null")
    @NotEmpty(message = "sellername should not be empty")
    @NotBlank(message = "sellername should not be blank")
    private String sellername;

    private String buyername;

    @Column
    @NotNull(message = "sellername should not be null")
    @NotEmpty(message = "sellername should not be empty")
    @NotBlank(message = "sellername should not be blank")
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
