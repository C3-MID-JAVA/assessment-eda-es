package ec.com.sofka.mapper;

import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.dto.AccountResponseDTO;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.request.CreateAccountRequest;
import ec.com.sofka.responses.CreateAccountResponse;

public class AccountMapper {
    public static AccountResponseDTO fromEntity(CreateAccountResponse createAccountResponse){
        return new AccountResponseDTO(
                createAccountResponse.getCustomerId(),
                createAccountResponse.getAccountNumber(),
                createAccountResponse.getBalance(),
                createAccountResponse.getUserId());
    }

    public static CreateAccountRequest toEntity(AccountRequestDTO accountRequestDTO){
        System.out.println("Llego aca?");
        return new CreateAccountRequest(accountRequestDTO.getUserId());
    }
}
