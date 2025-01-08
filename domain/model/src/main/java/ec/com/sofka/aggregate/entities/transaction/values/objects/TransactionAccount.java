package ec.com.sofka.aggregate.entities.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public record TransactionAccount(String value) implements IValueObject<String> {

    public TransactionAccount {
        if (value == null || !isValid(value)) {
            throw new IllegalArgumentException("Invalid account number.");
        }
    }

    public static TransactionAccount of(final String value) {
        return new TransactionAccount(value);
    }

    private boolean isValid(final String value) {
        return value.matches("\\d{10}");
    }

    @Override
    public String getValue() {
        return this.value;
    }

}