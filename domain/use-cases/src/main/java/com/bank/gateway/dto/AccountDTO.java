package com.bank.gateway.dto;

import java.math.BigDecimal;

public class AccountDTO {
    private String accountId;
    private String accountNumber;
    private String holder;
    private BigDecimal balance;
    private Boolean isActive;

    public AccountDTO(String accountId, String accountNumber, String holder, BigDecimal balance, Boolean isActive) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = balance;
        this.isActive = isActive;
    }

    public AccountDTO(String accountNumber, String holder, BigDecimal balance, Boolean isActive) {
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = balance;
        this.isActive = isActive;
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
