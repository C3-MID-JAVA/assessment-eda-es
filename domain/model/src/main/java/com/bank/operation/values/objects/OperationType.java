package com.bank.operation.values.objects;

import java.math.BigDecimal;

public record OperationType(String name, BigDecimal cost, OperationAction action) {

    @Override
    public String toString() {
        return "OperationType{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", action=" + action +
                '}';
    }
}
