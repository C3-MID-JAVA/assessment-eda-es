package com.bank.gateway.dto.mapper;

import com.bank.account.Account;
import com.bank.account.values.AccountId;
import com.bank.account.values.objects.AccountNumber;
import com.bank.account.values.objects.Balance;
import com.bank.account.values.objects.Holder;
import com.bank.account.values.objects.IsActive;
import com.bank.gateway.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOMapper {
    public static AccountDTO toDTO(Account acc) {
        return new AccountDTO(
                acc.getId().getValue(),
                acc.getNumber().getValue(),
                acc.getHolder().getValue(),
                acc.getBalance().getValue(),
                acc.getIsActive().getValue()
        );
    }

    public static Account toModel(AccountDTO dto) {
        return new Account(
                AccountId.of(dto.getAccountId()),
                AccountNumber.of(dto.getAccountNumber()),
                Holder.of(dto.getHolder()),
                Balance.of(dto.getBalance()),
                IsActive.of(dto.getActive())
        );
    }
}
