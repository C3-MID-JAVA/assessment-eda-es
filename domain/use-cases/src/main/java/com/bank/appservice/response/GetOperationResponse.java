package com.bank.appservice.response;

import com.bank.account.Account;
import com.bank.operation.values.objects.OperationTypesEnum;

import java.math.BigDecimal;

public class GetOperationResponse {
    private final String id;
    private final BigDecimal value;
    private final OperationTypesEnum type;
    private final Account account;

    public GetOperationResponse(String id, BigDecimal value, OperationTypesEnum type, Account account) {
        this.id = id;
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
