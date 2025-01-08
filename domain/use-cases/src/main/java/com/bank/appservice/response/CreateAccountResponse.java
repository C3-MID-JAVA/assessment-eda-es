package com.bank.appservice.response;

import java.math.BigDecimal;

public class CreateAccountResponse {
    private final String customerId;
    private final String accountId;
    private final String accountNumber;
    private final String holder;
    private final BigDecimal balance;
    private final Boolean isActive;

    public CreateAccountResponse(String customerId, String accountId, String accountNumber, String holder, BigDecimal balance, Boolean isActive) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = balance;
        this.isActive = isActive;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolder() {
        return holder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Boolean getActive() {
        return isActive;
    }
}
