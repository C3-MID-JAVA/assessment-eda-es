package com.bank.gateway.dto;

import com.bank.account.Account;
import com.bank.operation.values.objects.OperationTypesEnum;

import java.math.BigDecimal;

public class OperationDTO {
    private String id;
    private BigDecimal value;
    private OperationTypesEnum type;
    private Account account;

    public OperationDTO(String id, BigDecimal value, OperationTypesEnum type, Account account) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.account = account;
    }

    public OperationDTO(BigDecimal value, OperationTypesEnum type, Account account) {
        this.value = value;
        this.type = type;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public OperationTypesEnum getType() {
        return type;
    }

    public Account getAccount() {
        return account;
    }
}
