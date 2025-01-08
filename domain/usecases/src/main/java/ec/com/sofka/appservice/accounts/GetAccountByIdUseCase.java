package ec.com.sofka.appservice.accounts;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.data.response.AccountResponse;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Mono;

public class GetAccountByIdUseCase implements IUseCase<GetByElementRequest, AccountResponse> {

    private final IAccountRepository repository;
    private final IEventStore eventRepository;
    public GetAccountByIdUseCase(IAccountRepository repository, IEventStore eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }
/*
    @Override
    public Mono<AccountResponse> execute(GetByUserIdRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()  // Convierte el Flux<DomainEvent> en un Mono<List<DomainEvent>>
                .flatMap(events -> {
                    // Convertir List<DomainEvent> a Flux<DomainEvent>
                    Flux<DomainEvent> eventsFlux = Flux.fromIterable(events);

                    // Reconstruir el aggregate
                    return Customer.from(request.getAggregateId(), eventsFlux)
                            .flatMap(customer -> {
                                if (customer.getAccount() == null || customer.getAccount().getAccountNumber() == null) {
                                    return Mono.error(new IllegalStateException("Customer does not have a valid customerId"));
                                }

                                return repository.findById(customer.getAccount().getAccountNumber().getValue())
                                        .map(result -> new AccountResponse(
                                                request.getAggregateId(),
                                                result.getId(),
                                                result.getAccountNumber(),
                                                result.getName(),
                                                result.getBalance(),
                                                result.getStatus()
                                        ));
                            });
                });
    }

/*
    public Mono<Account> apply(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No account found with id: " + id)));
    }*/

    @Override
    public Mono<AccountResponse> execute(GetByElementRequest request) {

        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .map(eventsList -> Customer.from(request.getAggregateId(), eventsList))
                .flatMap(accountAggregate ->
                        repository.findById(accountAggregate.getAccount().getId().getValue())
                                .map(result -> new AccountResponse(
                                        request.getAggregateId(),
                                        result.getAccountId(),
                                        result.getAccountNumber(),
                                        result.getName(),
                                        result.getBalance(),
                                        result.getStatus()
                                ))
                );
    }

}
