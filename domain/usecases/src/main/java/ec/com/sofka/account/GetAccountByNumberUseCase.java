package ec.com.sofka.account;

import ec.com.sofka.account.request.GetAccountByNumberRequest;
import ec.com.sofka.account.responses.AccountResponse;
import ec.com.sofka.aggregate.customer.Customer;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class GetAccountByNumberUseCase implements IUseCaseExecute<GetAccountByNumberRequest, AccountResponse> {
    private final AccountRepository accountRepository;
    private final IEventStore repository;

    public GetAccountByNumberUseCase(AccountRepository accountRepository, IEventStore repository) {
        this.accountRepository = accountRepository;
        this.repository = repository;
    }

    @Override
    public Mono<AccountResponse> execute(GetAccountByNumberRequest cmd) {
        Flux<DomainEvent> events = repository.findAggregate(cmd.getAggregateId(), "customer");

        return Customer.from(cmd.getAggregateId(), events)
                .flatMap(customer -> Mono.justOrEmpty(
                                        customer.getAccounts().stream()
                                                .filter(account -> account.getAccountNumber().getValue().equals(cmd.getAccountNumber()))
                                                .findFirst()
                                )
                                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
                                .map(account -> new AccountResponse(
                                        customer.getId().getValue(),
                                        account.getAccountNumber().getValue(),
                                        account.getBalance().getValue(),
                                        account.getUserId().getValue()
                                ))
                );
    }
}
