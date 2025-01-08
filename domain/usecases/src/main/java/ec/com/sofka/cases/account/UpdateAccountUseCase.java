package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateAccountUseCase implements IUseCaseExecute<AccountRequest, AccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public UpdateAccountUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<AccountResponse> execute(AccountRequest request) {

        return repository.findAggregate(request.getAggregateId())
                .collectList()
                .map(domainEvent -> AccountAggregate.from(request.getAggregateId(), domainEvent))
                .flatMap(accountAggregate -> {
                    accountAggregate.updateAccount(
                            accountAggregate.getAccount().getId().getValue(),
                            accountAggregate.getAccount().getAccountNumber().getValue(),
                            request.getBalance(),
                            request.getStatus(),
                            accountAggregate.getAccount().getUser()
                    );

                    return accountRepository.save(AccountMapper.mapToDTOFromModel(accountAggregate.getAccount()))
                            .flatMap(account -> Flux.fromIterable(accountAggregate.getUncommittedEvents())
                                    .flatMap(repository::save)
                                    .then(Mono.just(account)))
                            .then(Mono.fromCallable(() -> {
                                accountAggregate.markEventsAsCommitted();
                                return AccountMapper.mapToResponseFromModel(accountAggregate.getAccount());
                            }));
                });

    }

}