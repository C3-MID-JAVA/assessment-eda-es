package com.bank.account.values.objects;

import com.bank.generics.interfaces.IValueObject;

public class Holder implements IValueObject<String> {
    private final String value;

    private Holder(final String value) {
        this.value = validate(value);
    }

    public static Holder of(final String value) {
        return new Holder(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    private String validate(final String value){
        /*if(value == null){
            throw new IllegalArgumentException("The personal data can't be null");
        }

        if(value.isBlank()){
            throw new IllegalArgumentException("The personal data can't be empty");
        }

        if(value.length() < 2){
            throw new IllegalArgumentException("The personal data must have at least 2 characters");
        }*/

        return value;
    }
}
