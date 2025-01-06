package ec.com.sofka.mapper;

import ec.com.sofka.account.Account;
import ec.com.sofka.appservice.gateway.dto.AccountDTO;
import ec.com.sofka.data.AccountEntity;

public class AccountMapper {

    public static AccountEntity DtoToEntity(AccountDTO accountDTO) {
        return new AccountEntity(accountDTO.getBalance(),
                accountDTO.getOwner(),
                accountDTO.getAccountNumber()
        );
    }

    public static AccountDTO entityToDTO(AccountEntity accountEntity) {
        return new AccountDTO(
                accountEntity.getBalance(),
                accountEntity.getAccountNumber(),
                accountEntity.getOwner()
        );
    }
}

