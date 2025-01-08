package com.bank.aggregate.events;

import com.bank.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountUpdated extends DomainEvent {
    private String accountId;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String accountHolder;
    private Boolean accountIsActive;

    public AccountUpdated(String accountId, String accountNumber, BigDecimal accountBalance, String accountHolder, Boolean accountIsActive) {
        super(EventsEnum.ACCOUNT_UPDATED.name());
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountHolder = accountHolder;
        this.accountIsActive = accountIsActive;
    }

    public AccountUpdated() {
        super(EventsEnum.ACCOUNT_UPDATED.name());
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public Boolean getAccountIsActive() {
        return accountIsActive;
    }
}
