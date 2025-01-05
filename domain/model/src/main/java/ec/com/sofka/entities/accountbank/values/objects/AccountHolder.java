package ec.com.sofka.entities.accountbank.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class AccountHolder implements IValueObject<String> {
    private final String value;

    private AccountHolder(final String value) {
        this.value = value;
    }

    public static AccountHolder of(final String value) {
        return new AccountHolder(value);
    }

    @Override
    public String getValue() {
        return value;
    }


    private String validate(final String value){

        if(value == null){
            throw new IllegalArgumentException("Value cannot be null");
        }

        if(value.isBlank()){
            throw new IllegalArgumentException("Value cannot be blank");
        }

        if(value.length() < 3){
            throw new IllegalArgumentException("Value must be at least 3 characters");
        }

        return value;

    }

}
