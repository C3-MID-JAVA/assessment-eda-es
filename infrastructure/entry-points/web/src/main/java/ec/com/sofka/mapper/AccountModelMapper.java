package ec.com.sofka.mapper;

import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.responses.AccountResponse;
import ec.com.sofka.utils.enums.StatusEnum;
import org.springframework.stereotype.Component;

@Component
public class AccountModelMapper {

    public static AccountRequest mapToRequest(AccountRequestDTO account) {
        if (account == null) {
            return null;
        }

        return new AccountRequest(
                account.getAccountNumber(),
                account.getBalance(),
                StatusEnum.valueOf(account.getStatus()),
                null,
                account.getUserAggregateId()
    );

    }

    public static AccountResponseDTO mapToDTO(AccountResponse account) {
        if (account == null) {
            return null;
        }

        return new AccountResponseDTO(
                account.getNumber(),
                account.getBalance(),
                account.getStatus().name(),
                UserModelMapper.mapToDTO(account.getUser())
        );
    }

}