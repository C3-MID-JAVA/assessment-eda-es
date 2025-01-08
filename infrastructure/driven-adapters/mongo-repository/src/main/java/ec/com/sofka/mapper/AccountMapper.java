package ec.com.sofka.mapper;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.gateway.dto.AccountDTO;

public class AccountMapper {
    public static AccountEntity toEntity(AccountDTO accountDTO) {
        return new AccountEntity(
                accountDTO.getId(),
                accountDTO.getBalance(),
                accountDTO.getOwner(),
                accountDTO.getAccountNumber(),
                accountDTO.getIdUser(),
                accountDTO.getStatus()
                );
    }

    public static AccountDTO toDTO(AccountEntity accountEntity) {
        return new AccountDTO(
                accountEntity.getAccountId(),
                accountEntity.getBalance(),
                accountEntity.getAccountNumber(),
                accountEntity.getOwner(),
                accountEntity.getIdUser(),
                accountEntity.getStatus()
        );
    }
}
