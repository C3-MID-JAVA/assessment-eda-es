package ec.com.sofka.mapper;

import ec.com.sofka.appservice.data.request.CreateAccountRequest;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.data.request.UpdateAccountRequest;
import ec.com.sofka.appservice.data.response.AccountResponse;
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
    public static AccountResponseDTO toAccountResponseDTO(AccountResponse response) {
        return new AccountResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus()
        );
    }

    public static GetByElementRequest toGetAccountRequest(AccountRequestDTO request) {
        return new GetByElementRequest(
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
