package ec.com.sofka.aggregate.entities.account.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;


public record  AccountNumber(String value) implements IValueObject<String> {

    public AccountNumber(final String value) {
        this.value = validate(value);
    }

    public static AccountNumber of(final String value) {
        return new AccountNumber(value);
    }


    private String validate(final String value) {
        if (value == null || !value.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid number account.");
        }
        return value;
    }

    @Override
    public String getValue() {
        return value;
    }

}