package ec.com.sofka.account;

import ec.com.sofka.account.request.GetAllByUserIdRequest;
import ec.com.sofka.account.responses.AccountResponse;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Flux;

public class GetAllByUserIdUseCase implements IUseCase<GetAllByUserIdRequest, AccountResponse> {

    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public GetAllByUserIdUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Flux<AccountResponse> execute(GetAllByUserIdRequest request) {
        return accountRepository.getAllByUserId(request.getUserId())
                .flatMap(accountDTO -> repository.findAggregate(accountDTO.getCustomerId())
                        .collectList()
                        .flatMapMany(events -> Customer.from(accountDTO.getCustomerId(), Flux.fromIterable(events))
                                .flatMapMany(customer -> {
                                    customer.retrieveAccount(
                                            accountDTO.getBalance(),
                                            accountDTO.getAccountNumber(),
                                            accountDTO.getUserId()
                                    );

                                    return Flux.fromIterable(customer.getUncommittedEvents())
                                            .flatMap(repository::save)
                                            .doOnTerminate(customer::markEventsAsCommitted)
                                            .thenMany(Flux.just(new AccountResponse(
                                                    customer.getId().getValue(),
                                                    accountDTO.getAccountNumber(),
                                                    accountDTO.getBalance(),
                                                    accountDTO.getUserId()
                                            )));
                                })
                        )
                );
    }

}
