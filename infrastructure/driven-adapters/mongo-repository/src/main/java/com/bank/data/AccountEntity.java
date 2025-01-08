package com.bank.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "accounts")
public class AccountEntity {
    @Id
    private String id;

    @Field("account_number")
    private String accountNumber;

    @Field("account_holder")
    private String holder;

    @Field("account_balance")
    private BigDecimal balance;

    @Field("is_active")
    private Boolean isActive;

    @DBRef
    private List<OperationEntity> operations;

    public AccountEntity() {}

    public AccountEntity(String id) {
        this.id = id;
    }

    public AccountEntity(String id, String accountNumber, String holder, BigDecimal balance, Boolean isActive) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<OperationEntity> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationEntity> operations) {
        this.operations = operations;
    }
}