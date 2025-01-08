package com.bank.operation.values.objects;

import com.bank.generics.interfaces.IValueObject;

public class Type implements IValueObject<OperationTypesEnum> {
    private final OperationTypesEnum value;

    private Type(final OperationTypesEnum value) {
        this.value = validate(value);
    }

    public static Type of(final OperationTypesEnum value) {
        return new Type(value);
    }

    @Override
    public OperationTypesEnum getValue() {
        return value;
    }

    private OperationTypesEnum validate(final OperationTypesEnum value){
        if(value == null){
            throw new IllegalArgumentException("The type can't be null");
        }

        if(value.name().isBlank()){
            throw new IllegalArgumentException("The type can't be empty");
        }

        return value;
    }
}
