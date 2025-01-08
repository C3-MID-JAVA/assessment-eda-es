package com.bank.aggregate.events;

import com.bank.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {
    private String accountId;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String accountHolder;
    private Boolean accountIsActive;

    public AccountCreated(String accountId, String accountNumber, BigDecimal accountBalance, String accountHolder, Boolean accountIsActive) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountHolder = accountHolder;
        this.accountIsActive = accountIsActive;
    }

    public AccountCreated(String accountNumber, BigDecimal accountBalance, String accountHolder, Boolean accountIsActive) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountHolder = accountHolder;
        this.accountIsActive = accountIsActive;
    }

    public AccountCreated() {
        super(EventsEnum.ACCOUNT_CREATED.name());

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
