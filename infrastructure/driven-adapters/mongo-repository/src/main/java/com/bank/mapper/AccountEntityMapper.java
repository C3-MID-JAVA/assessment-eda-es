package com.bank.mapper;

import com.bank.account.Account;
import com.bank.account.values.AccountId;
import com.bank.account.values.objects.AccountNumber;
import com.bank.account.values.objects.Balance;
import com.bank.account.values.objects.Holder;
import com.bank.account.values.objects.IsActive;
import com.bank.data.AccountEntity;

public class AccountEntityMapper {
    public static AccountEntity toAccountEntity(Account acc) {
        AccountEntity entity = new AccountEntity();

        entity.setId(acc.getId().getValue());
        entity.setAccountNumber(acc.getNumber().getValue());
        entity.setHolder(acc.getHolder().getValue());
        entity.setBalance(acc.getBalance().getValue());

        return entity;
    }

    public static Account toAccount(AccountEntity entity) {
        return new Account(
                AccountId.of(entity.getId()),
                AccountNumber.of(entity.getAccountNumber()),
                Holder.of(entity.getHolder()),
                Balance.of(entity.getBalance()),
                IsActive.of(entity.getActive())
        );
    }
}
