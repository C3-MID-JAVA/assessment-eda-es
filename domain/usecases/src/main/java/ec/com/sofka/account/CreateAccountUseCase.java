package ec.com.sofka.account;


import ec.com.sofka.aggregate.customer.Customer;
import ec.com.sofka.account.request.CreateAccountRequest;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.account.responses.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateAccountUseCase implements IUseCase<CreateAccountRequest, AccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public Mono<AccountResponse> execute(CreateAccountRequest cmd) {
        Flux<DomainEvent> events = repository.findAggregate(cmd.getAggregateId(), "customer");

        return Customer.from(cmd.getAggregateId(), events)
                .flatMap(customer -> {
                    if (customer.getUser() == null) {
                        return Mono.error(new NotFoundException("User not found"));
                    }

                    String generatedAccountNumber = UUID.randomUUID().toString().substring(0, 8);

                    customer.createAccount(BigDecimal.valueOf(0), generatedAccountNumber, customer.getUser().getId().getValue());

                    Account newAccount = customer.getAccounts().stream()
                            .filter(account -> account.getAccountNumber().getValue().equals(generatedAccountNumber))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Account creation failed"));

                    return accountRepository.save(new AccountDTO(
                                    newAccount.getId().getValue(),
                                    newAccount.getAccountNumber().getValue(),
                                    newAccount.getBalance().getValue(),
                                    customer.getUser().getId().getValue()
                            ))
                            .flatMap(savedAccount -> {
                                return Flux.fromIterable(customer.getUncommittedEvents())
                                        .flatMap(repository::save)
                                        .then(Mono.just(savedAccount));
                            })
                            .doOnTerminate(customer::markEventsAsCommitted)
                            .thenReturn(new AccountResponse(
                                    customer.getId().getValue(),
                                    newAccount.getAccountNumber().getValue(),
                                    newAccount.getBalance().getValue(),
                                    newAccount.getUserId().getValue()
                            ));
                });
    }
}
