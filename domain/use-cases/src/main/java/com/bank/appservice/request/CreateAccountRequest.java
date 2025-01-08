package com.bank.appservice.request;

import com.bank.generics.utils.Request;

import java.math.BigDecimal;

public class CreateAccountRequest extends Request {
    private final String accountId;
    private final String accountNumber;
    private final BigDecimal accountBalance;
    private final String accountHolder;
    private final Boolean accountIsActive;

    public CreateAccountRequest(final String accountId, final String accountNumber, final BigDecimal accountBalance, final String accountHolder, final Boolean accountIsActive) {
        super(null);
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountHolder = accountHolder;
        this.accountIsActive = accountIsActive;
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
