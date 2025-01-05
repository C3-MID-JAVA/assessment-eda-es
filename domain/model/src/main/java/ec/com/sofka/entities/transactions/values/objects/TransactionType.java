package ec.com.sofka.entities.transactions.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;
import ec.com.sofka.utils.TransactionsTypes;

public class TransactionType implements IValueObject<String> {
    private final String value;

    public TransactionType(String value) {
        this.value = value;
    }

    public static TransactionType of(String value) {
        return new TransactionType(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    private String validate(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("The transaction type can't be null");
        }

        if (value.isBlank()) {
            throw new IllegalArgumentException("The transaction type can't be empty");
        }

        try {
            // Verifica si el valor coincide con alguno de los enums
            TransactionsTypes.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type: " + value);
        }

        return value;
    }

}
