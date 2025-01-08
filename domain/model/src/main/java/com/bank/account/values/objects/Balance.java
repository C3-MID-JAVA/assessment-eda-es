package com.bank.account.values.objects;

import com.bank.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public class Balance implements IValueObject<BigDecimal> {
    private final BigDecimal value;

    private Balance(final BigDecimal value) {
        this.value = validate(value);
    }

    public static Balance of(final BigDecimal value) {
        return new Balance(value);
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    private BigDecimal validate(final BigDecimal value){
        if(value == null){
            throw new IllegalArgumentException("The balance can't be null");
        }

        return value;
    }
}