package com.bank.appservice.request;

import com.bank.account.Account;
import com.bank.generics.utils.Request;
import com.bank.operation.values.objects.OperationTypesEnum;

import java.math.BigDecimal;

public class GetOperationByIdRequest extends Request {
    private final String id;

    public GetOperationByIdRequest(String aggregateId, String id) {
        super(aggregateId);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
