package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.cases.user.FindUserByIdUseCase;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase implements IUseCaseExecute<AccountRequest, AccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public CreateAccountUseCase(IEventStore repository, AccountRepository accountRepository, FindUserByIdUseCase findUserByIdUseCase) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    public Mono<AccountResponse> execute(AccountRequest accountRequest) {
        AccountAggregate accountAggregate = new AccountAggregate();

        return findUserByIdUseCase.getUserByAggregate(accountRequest.getUserAggregateId())
                .flatMap(user -> {
                    accountAggregate.createAccount(
                            accountRequest.getAccountNumber(),
                            accountRequest.getBalance(),
                            accountRequest.getStatus(),
                            user
                    );
                    return accountRepository.save(AccountMapper.mapToDTOFromModel(accountAggregate.getAccount()))
                            .flatMap(account -> Flux.fromIterable(accountAggregate.getUncommittedEvents())
                                    .flatMap(repository::save)
                                    .then(Mono.just(account))
                            )
                            .mapNotNull(savedAccount -> {
                                accountAggregate.markEventsAsCommitted();
                                return AccountMapper.mapToResponseFromModel(accountAggregate.getAccount());
                            });
                });
    }

}