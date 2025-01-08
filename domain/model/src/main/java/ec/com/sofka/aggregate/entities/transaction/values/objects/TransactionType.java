package ec.com.sofka.aggregate.entities.transaction.values.objects;

import ec.com.sofka.aggregate.entities.transaction.values.TransactionTypeEnum;
import ec.com.sofka.generics.interfaces.IValueObject;

public record TransactionType(String value) implements IValueObject<String> {

    public TransactionType(String value) {
        this.value = validate(value);
    }

    public static TransactionType of(String value) {
        return new TransactionType(value);
    }

    private String validate(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Transaction type cannot be null or empty");
        }

        try {
            TransactionTypeEnum.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type: " + value);
        }

        return value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}