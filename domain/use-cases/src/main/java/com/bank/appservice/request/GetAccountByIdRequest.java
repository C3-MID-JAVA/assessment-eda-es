package com.bank.appservice.request;

import com.bank.generics.utils.Request;

import java.math.BigDecimal;

public class GetAccountByIdRequest extends Request {
    private final String id;

    public GetAccountByIdRequest(String aggregateId, String id) {
        super(aggregateId);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
