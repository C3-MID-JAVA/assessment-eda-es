package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateAccountUseCase implements IUseCaseExecute<AccountRequest, Mono<AccountResponse>> {
    private final IAccountRepository accountRepository;
    private final IEventStore eventRepository;

    public UpdateAccountUseCase(IAccountRepository accountRepository, IEventStore eventRepository) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<AccountResponse> execute(AccountRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    Customer customer = Customer.from(request.getAggregateId(), events);

                    customer.updateAccount(
                            customer.getAccount().getId().getValue(),
                            request.getBalance(),
                            request.getNumber(),
                            request.getCustomerName(),
                            request.getStatus()
                    );

                    return accountRepository.update(new AccountDTO(
                                    customer.getAccount().getId().getValue(),
                                    request.getCustomerName(),
                                    request.getNumber(),
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getStatus()
                            ))
                            .flatMap(updatedAccount -> Flux.fromIterable(customer.getUncommittedEvents())
                                    .flatMap(eventRepository::save)
                                    .then(Mono.just(updatedAccount))
                            )
                            .mapNotNull(updatedAccount -> {
                                customer.markEventsAsCommitted();
                                return new AccountResponse(
                                        request.getAggregateId(),
                                        updatedAccount.getId(),
                                        updatedAccount.getAccountNumber(),
                                        updatedAccount.getName(),
                                        updatedAccount.getBalance(),
                                        updatedAccount.getStatus()
                                );
                            });
                });
    }
}
