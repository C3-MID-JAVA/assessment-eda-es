package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase implements IUseCaseExecute<AccountRequest, Mono<AccountResponse>> {
    private final IEventStore repository;
    private final IAccountRepository accountRepository;

    public CreateAccountUseCase(IEventStore repository, IAccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<AccountResponse> execute(AccountRequest request) {
        Customer customerAggregate = new Customer();

        return Mono.fromRunnable(() ->
                        customerAggregate.createAccount(
                                request.getBalance(),
                                request.getNumber(),
                                request.getCustomerName(),
                                request.getStatus()
                        )
                )
                .then(
                        accountRepository.save(new AccountDTO(
                                customerAggregate.getAccount().getId().getValue(),
                                customerAggregate.getAccount().getName().getValue(),
                                customerAggregate.getAccount().getNumber().getValue(),
                                customerAggregate.getAccount().getBalance().getValue(),
                                customerAggregate.getAccount().getStatus()
                        ))
                )
                .flatMap(savedAccount ->
                        Flux.fromIterable(customerAggregate.getUncommittedEvents())
                                .flatMap(repository::save)
                                .then(Mono.just(savedAccount))
                )
                .mapNotNull(savedAccount -> {
                    customerAggregate.markEventsAsCommitted();
                    return new AccountResponse(
                            customerAggregate.getId().getValue(),
                            customerAggregate.getAccount().getId().getValue(),
                            customerAggregate.getAccount().getNumber().getValue(),
                            customerAggregate.getAccount().getName().getValue(),
                            customerAggregate.getAccount().getBalance().getValue(),
                            customerAggregate.getAccount().getStatus()
                    );
                });
    }
}
