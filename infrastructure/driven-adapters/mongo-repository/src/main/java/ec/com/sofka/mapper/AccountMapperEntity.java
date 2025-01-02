package ec.com.sofka.mapper;

import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.gateway.dto.AccountDTO;

public class AccountMapperEntity {
    public static AccountEntity toEntity(AccountDTO accountDTO) {
        return new AccountEntity(
                accountDTO.getAccountNumber(),
                accountDTO.getBalance(),
                accountDTO.getUserId()
        );
    }

    public static AccountDTO toDTO(AccountEntity accountEntity) {
        return new AccountDTO(
                accountEntity.getId(),
                accountEntity.getBalance(),
                accountEntity.getAccountNumber(),
                accountEntity.getUserId()
        );
    }
}
