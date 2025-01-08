package ec.com.sofka.mapper;

import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.dto.AccountResponseDTO;
import ec.com.sofka.account.request.CreateAccountRequest;
import ec.com.sofka.account.responses.AccountResponse;

public class AccountMapper {
    public static AccountResponseDTO fromEntity(AccountResponse accountResponse) {
        return new AccountResponseDTO(
                accountResponse.getCustomerId(),
                accountResponse.getAccountNumber(),
                accountResponse.getBalance(),
                accountResponse.getUserId());
    }

    public static CreateAccountRequest toEntity(AccountRequestDTO accountRequestDTO) {
        return new CreateAccountRequest(accountRequestDTO.getAggregateId());
    }
}
