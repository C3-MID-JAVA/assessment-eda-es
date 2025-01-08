package com.bank.operation.values;

import com.bank.generics.utils.Identity;

public class OperationId extends Identity {
    public OperationId() {
        super();
    }

    private OperationId(final String id) {
        super(id);
    }

    public static OperationId of(final String id) {
        return new OperationId(id);
    }
}
