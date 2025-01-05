package ec.com.sofka.entities.transactions.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public class Amount implements IValueObject<BigDecimal> {

    private final BigDecimal value;

    public Amount(BigDecimal value) {
        this.value = value;
    }

    public static Amount of(final BigDecimal amount) {
        return new Amount(amount);
    }

    private BigDecimal validate(final BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("The amount can't be null");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The amount can't be negative");
        }

        return value;
    }

    @Override
    public BigDecimal getValue() {
        return null;
    }

}
