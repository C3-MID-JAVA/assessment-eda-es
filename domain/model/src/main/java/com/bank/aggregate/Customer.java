package com.bank.aggregate;

import com.bank.account.Account;
import com.bank.account.values.AccountId;
import com.bank.aggregate.events.AccountCreated;
import com.bank.aggregate.events.AccountUpdated;
import com.bank.aggregate.events.OperationCreated;
import com.bank.aggregate.values.CustomerId;
import com.bank.generics.domain.DomainEvent;
import com.bank.generics.utils.AggregateRoot;
import com.bank.operation.Operation;
import com.bank.operation.values.objects.OperationTypesEnum;

import java.math.BigDecimal;

public class Customer extends AggregateRoot<CustomerId> {
    private Account account;
    private Operation operation;

    public Customer() {
        super(new CustomerId());
        setSubscription(new CustomerHandler(this));
    }

    private Customer(final String id) {
        super(CustomerId.of(id));
        setSubscription(new CustomerHandler(this));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void createAccount(String accountNumber, String holder, BigDecimal accountBalance, Boolean isActive) {
        addEvent(new AccountCreated(new AccountId().getValue(), accountNumber, accountBalance, holder, isActive)).apply();
    }

    public void updateAccount(String accountId, BigDecimal balance, String accountNumber, String holder, Boolean isActive ) {
        addEvent(new AccountUpdated(accountId, accountNumber, balance, holder, isActive)).apply();

    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void createOperation(OperationTypesEnum operationType, BigDecimal value, Account account) {
        addEvent(new OperationCreated(operationType, value, account)).apply();

    }

    public static Customer from(final String id, DomainEvent event) {
        Customer customer = new Customer(id);
        customer.addEvent(event).apply();
        return customer;
    }
}
