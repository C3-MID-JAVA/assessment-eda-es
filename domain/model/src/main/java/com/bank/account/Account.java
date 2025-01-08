package com.bank.account;

import com.bank.account.values.AccountId;
import com.bank.account.values.objects.AccountNumber;
import com.bank.account.values.objects.Balance;
import com.bank.account.values.objects.Holder;
import com.bank.account.values.objects.IsActive;
import com.bank.generics.utils.Entity;
import com.bank.operation.Operation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account extends Entity<AccountId> {
    private AccountNumber number;
    private Holder holder;
    private Balance balance;
    private IsActive isActive;

    public Account(AccountId id, AccountNumber number, Holder holder, Balance balance, IsActive isActive) {
        super(id);
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.isActive = isActive;
    }

    public AccountNumber getNumber() {
        return number;
    }

    public Holder getHolder() {
        return holder;
    }

    public Balance getBalance() {
        return balance;
    }

    public IsActive getIsActive() {
        return isActive;
    }
}