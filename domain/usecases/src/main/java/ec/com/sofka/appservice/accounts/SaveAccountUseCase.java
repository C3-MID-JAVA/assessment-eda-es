package ec.com.sofka.appservice.accounts;

import ec.com.sofka.account.Account;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

public class SaveAccountUseCase {

    private final IAccountRepository repository;

    public SaveAccountUseCase(IAccountRepository repository) {
        this.repository = repository;
    }
/*
    public Mono<Account> apply(Account account) {
        return repository.save(account);
    }*/
}
