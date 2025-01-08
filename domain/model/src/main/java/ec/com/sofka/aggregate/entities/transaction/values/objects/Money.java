package ec.com.sofka.aggregate.entities.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public record Money(BigDecimal value) implements IValueObject<BigDecimal> {

    public Money(final BigDecimal value) {
        this.value = validate(value);
    }

    public static Money of(final BigDecimal value) {
        return new Money(value);
    }

    private BigDecimal validate(final BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The quantity must be positive.");
        }
        return value;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

}