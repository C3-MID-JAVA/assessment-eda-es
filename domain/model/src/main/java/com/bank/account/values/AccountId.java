package com.bank.account.values;

import com.bank.generics.utils.Identity;

public class AccountId extends Identity {
    public AccountId() {
        super();
    }

    private AccountId(final String id) {
        super(id);
    }

    public static AccountId of(final String id) {
        return new AccountId(id);
    }
}
