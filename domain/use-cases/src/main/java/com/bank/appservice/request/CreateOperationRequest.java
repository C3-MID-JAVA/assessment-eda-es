package com.bank.appservice.request;

import com.bank.account.Account;
import com.bank.generics.utils.Request;
import com.bank.operation.values.objects.OperationTypesEnum;

import java.math.BigDecimal;

public class CreateOperationRequest extends Request {
    private final BigDecimal value;
    private final OperationTypesEnum type;
    private final Account account;

    public CreateOperationRequest(BigDecimal value, OperationTypesEnum type, Account account) {
        super(null);
        this.value = value;
        this.type = type;
        this.account = account;
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
