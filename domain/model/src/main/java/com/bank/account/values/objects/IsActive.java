package com.bank.account.values.objects;

import com.bank.generics.interfaces.IValueObject;

public class IsActive implements IValueObject<Boolean> {
    private final Boolean value;

    private IsActive(final Boolean value) {
        this.value = validate(value);
    }

    public static IsActive of(final Boolean value) {
        return new IsActive(value);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    private Boolean validate(final Boolean value){
        /*if(value == null){
            throw new IllegalArgumentException("isActive can't be null");
        }*/

        return value;
    }

}
