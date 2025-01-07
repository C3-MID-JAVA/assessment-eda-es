package ec.com.sofka.mapper;

import ec.com.sofka.account.Account;
import ec.com.sofka.appservice.accounts.request.CreateAccountRequest;
import ec.com.sofka.appservice.accounts.request.GetAccountRequest;
import ec.com.sofka.appservice.accounts.request.UpdateAccountRequest;
import ec.com.sofka.appservice.accounts.response.CreateAccountResponse;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;

public class AccountDTOMapper {
    // Convierte AccountRequestDTO a CreateAccountRequest
    public static CreateAccountRequest toCreateAccountRequest(AccountRequestDTO requestDTO) {
        return new CreateAccountRequest(
                requestDTO.getAccountNumber(),
                requestDTO.getOwner(),
                requestDTO.getInitialBalance()
        );
    }

    // Convierte la respuesta del caso de uso a AccountResponseDTO
    public static AccountResponseDTO toAccountResponseDTO(CreateAccountResponse response) {
        return new AccountResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus()
        );
    }

    public static GetAccountRequest toGetAccountRequest(AccountRequestDTO request) {
        return new GetAccountRequest(
                request.getCustomerId(),
                request.getAccountNumber()
        );
    }

    public static UpdateAccountRequest toUpdateAccountRequest(AccountRequestDTO request) {
        return new UpdateAccountRequest(
                request.getCustomerId(),
                request.getInitialBalance(),
                request.getAccountNumber(),
                request.getOwner(),
                request.getStatus()
        );
    }
}
