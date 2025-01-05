package ec.com.sofka.entities.transactions.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public class TransactionCost implements IValueObject<BigDecimal> {
    private final BigDecimal value;

    public TransactionCost(BigDecimal value) {
        this.value = value;
    }

    public static TransactionCost of(BigDecimal value) {
        return new TransactionCost(value);
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    private BigDecimal validate(final BigDecimal value) {
        if(value == null){
            throw new IllegalArgumentException("The balance can't be null");
        }
        return value;
    }

}
