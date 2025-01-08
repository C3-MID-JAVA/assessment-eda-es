package com.bank.handler;

import com.bank.appservice.request.CreateAccountRequest;
import com.bank.appservice.request.GetAccountByIdRequest;
import com.bank.appservice.response.CreateAccountResponse;
import com.bank.appservice.response.GetAccountResponse;
import com.bank.appservice.usecases.account.CreateAccountUseCase;
import com.bank.appservice.usecases.account.GetAccountByIdUseCase;
import com.bank.appservice.usecases.account.GetAllAccountsUseCase;
import jakarta.validation.Valid;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Component
@Validated
public class AccountHandler {
    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    public AccountHandler(
            GetAllAccountsUseCase getAllAccountsUseCase,
            GetAccountByIdUseCase getAccountByIdUseCase,
            CreateAccountUseCase createAccountUseCase
    ) {
        this.getAllAccountsUseCase = getAllAccountsUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }


    public Flux<GetAccountResponse> findAllAccounts() {
        /*return getAllAccountsUseCase.apply()
                .map(AccountMapper::toDTO)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No accounts to list.")));*/
        return Flux.error(new NotImplementedException("Coming soon..."));
    }

    public Mono<GetAccountResponse> findAccountById(@Valid GetAccountByIdRequest request) {
        return getAccountByIdUseCase.execute(request)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Account with id " + request.getId() + " does not exist.")));
    }

    public Mono<CreateAccountResponse> createAccount(CreateAccountRequest request) {
        return createAccountUseCase.execute(request)
                .switchIfEmpty(Mono.error(new RuntimeException("Could not create account.")));
    }
}
