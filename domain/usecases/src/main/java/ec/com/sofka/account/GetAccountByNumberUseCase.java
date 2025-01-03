package ec.com.sofka.account;

import ec.com.sofka.account.request.GetAccountByNumberRequest;
import ec.com.sofka.account.responses.AccountResponse;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase implements IUseCase<GetAccountByNumberRequest, AccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public GetAccountByNumberUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<AccountResponse> execute(GetAccountByNumberRequest cmd) {
        return accountRepository.findByAccountNumber(cmd.getAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
                .flatMap(accountDTO -> repository.findAggregate(accountDTO.getCustomerId())
                        .collectList()
                        .flatMap(events -> Customer.from(accountDTO.getId(), Flux.fromIterable(events)))
                        .flatMap(customer -> {
                            customer.retrieveAccount(
                                    accountDTO.getBalance(),
                                    accountDTO.getAccountNumber(),
                                    accountDTO.getUserId()
                            );

                            return Flux.fromIterable(customer.getUncommittedEvents())
                                    .flatMap(repository::save)
                                    .doOnTerminate(customer::markEventsAsCommitted)
                                    .then(Mono.just(new AccountResponse(
                                            customer.getId().getValue(),
                                            accountDTO.getAccountNumber(),
                                            accountDTO.getBalance(),
                                            accountDTO.getUserId()
                                    )));
                        }));
    }
}
