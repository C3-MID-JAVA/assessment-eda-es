package com.bank.operation.values.objects;

import com.bank.account.values.objects.Balance;
import com.bank.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public class Value implements IValueObject<BigDecimal> {
    private final BigDecimal value;

    private Value(final BigDecimal value) {
        this.value = validate(value);
    }

    public static Value of(final BigDecimal value) {
        return new Value(value);
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    private BigDecimal validate(final BigDecimal value){
        if(value == null){
            throw new IllegalArgumentException("The value can't be null");
        }

        if(value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(("The value must be greater than 0"));
        }

        return value;
    }
}
