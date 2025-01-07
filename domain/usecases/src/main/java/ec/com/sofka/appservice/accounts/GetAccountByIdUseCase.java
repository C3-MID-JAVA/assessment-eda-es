package ec.com.sofka.appservice.accounts;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.accounts.request.GetByUserIdRequest;
import ec.com.sofka.appservice.accounts.response.CreateAccountResponse;
import ec.com.sofka.appservice.accounts.response.GetAccountResponse;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCase;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAccountByIdUseCase implements IUseCase<GetByUserIdRequest, GetAccountResponse> {

    private final IAccountRepository repository;
    private final IEventStore eventRepository;
    public GetAccountByIdUseCase(IAccountRepository repository, IEventStore eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<GetAccountResponse> execute(GetByUserIdRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()  // Convierte el Flux<DomainEvent> en un Mono<List<DomainEvent>>
                .flatMap(events -> {
                    // Convertir List<DomainEvent> a Flux<DomainEvent>
                    Flux<DomainEvent> eventsFlux = Flux.fromIterable(events);

                    // Reconstruir el aggregate
                    return Customer.from(request.getAggregateId(), eventsFlux)
                            .flatMap(customer -> {
                                if (customer.getAccount() == null || customer.getAccount().getAccountNumber() == null) {
                                    return Mono.error(new IllegalStateException("Customer does not have a valid account"));
                                }

                                return repository.findById(customer.getAccount().getAccountNumber().getValue())
                                        .map(result -> new GetAccountResponse(
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


}
