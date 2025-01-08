package com.bank.aggregate.events;

import com.bank.account.Account;
import com.bank.generics.domain.DomainEvent;
import com.bank.operation.values.objects.OperationTypesEnum;

import java.math.BigDecimal;

public class OperationCreated extends DomainEvent {
    private String operationId;
    private OperationTypesEnum operationType;
    private BigDecimal operationValue;
    private Account operationAccount;

    public OperationCreated(String operationId, OperationTypesEnum operationType, BigDecimal operationValue, Account operationAccount) {
        super(EventsEnum.OPERATION_CREATED.name());
        this.operationId = operationId;
        this.operationType = operationType;
        this.operationValue = operationValue;
        this.operationAccount = operationAccount;
    }

    public OperationCreated(OperationTypesEnum operationType, BigDecimal operationValue, Account operationAccount) {
        super(EventsEnum.OPERATION_CREATED.name());
        this.operationType = operationType;
        this.operationValue = operationValue;
        this.operationAccount = operationAccount;
    }

    public OperationCreated() {
        super(EventsEnum.OPERATION_CREATED.name());
    }

    public String getOperationId() {
        return operationId;
    }

    public OperationTypesEnum getOperationType() {
        return operationType;
    }

    public BigDecimal getOperationValue() {
        return operationValue;
    }

    public Account getOperationAccount() {
        return operationAccount;
    }
}