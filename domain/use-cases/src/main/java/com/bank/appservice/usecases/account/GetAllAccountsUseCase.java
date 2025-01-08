package com.bank.appservice.usecases.account;

import com.bank.account.Account;
import com.bank.gateway.IAccountRepository;
import com.bank.gateway.IBusMessage;
import reactor.core.publisher.Flux;

public class GetAllAccountsUseCase {
    /*private final IAccountRepository repository;
    private final IBusMessage busMessage;

    public GetAllAccountsUseCase(IAccountRepository repository, IBusMessage busMessage) {
        this.repository = repository;
        this.busMessage = busMessage;
    }

    public Flux<Account> apply() {
        busMessage.sendMsg("Getting all accounts.");
        return repository.findAllAccounts();
    }*/
}
