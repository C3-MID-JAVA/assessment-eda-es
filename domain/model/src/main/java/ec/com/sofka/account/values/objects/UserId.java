package ec.com.sofka.account.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class UserId implements IValueObject<String> {
    private final String value;

    private UserId(final String value) {
        this.value = validate(value);
    }

    public static UserId of(final String value) {
        return new UserId(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    private String validate(final String value){
        if(value == null){
            throw new IllegalArgumentException("The id can't be null");
        }

        if(value.isBlank()){
            throw new IllegalArgumentException("The id can't be empty");
        }

        if(value.length() < 8){
            throw new IllegalArgumentException("The id must be at least 8 characters long");
        }

        return value;
    }
}
