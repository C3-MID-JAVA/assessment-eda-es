package ec.com.sofka.handler;

import ec.com.sofka.appservice.accounts.*;
import ec.com.sofka.appservice.accounts.request.CreateAccountRequest;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final GetCheckBalanceUseCase getCheckBalanceUseCase;
    private final GetAllAccountsUseCase getAccountsUseCase;

    public AccountHandler(GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase, CreateAccountUseCase createAccountUseCase,
                          GetAllAccountsUseCase getAccountsUseCase, GetAccountByIdUseCase getAccountByIdUseCase, GetCheckBalanceUseCase getCheckBalanceUseCase) {
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountsUseCase = getAccountsUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.getCheckBalanceUseCase = getCheckBalanceUseCase;
    }
/*
    public Mono<AccountResponseDTO> getAccountByAccountNumber(String accountNumber) {
        return getAccountByAccountNumberUseCase.apply(accountNumber).map(AccountDTOMapper::accountToAccountResponseDTO);
    }*/

    public Mono<AccountResponseDTO> createAccount(AccountRequestDTO request) {
        // Ejecutar el caso de uso y mapear la respuesta a un ResponseDTO
        return createAccountUseCase.execute(
                        new CreateAccountRequest(
                                request.getInitialBalance(),
                                request.getAccountNumber(),
                                request.getOwner()
                        ))
                .map(response -> new AccountResponseDTO(
                        response.getCustomerId(),
                        response.getAccountNumber(),
                        response.getBalance(),
                        response.getName()));  // Mapear la respuesta reactiva
    }
/*
    public Mono<AccountResponseDTO> getAccountByAccountId(String accountId) {
        return getAccountByIdUseCase.apply(accountId)
                .map(AccountDTOMapper::accountToAccountResponseDTO);
    }

    public Mono<BigDecimal> getCheckBalance(String accountId) {
        return getCheckBalanceUseCase.apply(accountId);
    }

    public Flux<AccountResponseDTO> getAccounts() {
        return getAccountsUseCase.apply().map(AccountDTOMapper::accountToAccountResponseDTO);
    }*/

}
