package ec.com.sofka.appservice.accounts;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.data.response.AccountResponse;
import ec.com.sofka.appservice.gateway.IBusMessage;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetAllAccountsUseCase implements IUseCaseGet<AccountResponse> {

    private final IAccountRepository repository;
    private final IBusMessage busMessage;

    private final IEventStore eventRepository;


    public GetAllAccountsUseCase(IAccountRepository repository, IBusMessage busMessage, IEventStore eventRepository) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.eventRepository = eventRepository;
    }
    @Override
    public Flux<AccountResponse> get() {
        // Obtener todos los eventos de manera reactiva
        return eventRepository.findAllAggregates() // Suponiendo que retorna Flux<DomainEvent>
                .collectList() // Agrupar todos los eventos en una lista
                .flatMapMany(events -> {
                    // Procesar los últimos eventos
                    Map<String, List<DomainEvent>> eventsByAggregate = events.stream()
                            .collect(Collectors.groupingBy(DomainEvent::getAggregateRootId));

                    // Reconstruir los clientes de manera reactiva
                    return Flux.fromIterable(eventsByAggregate.entrySet())
                            .flatMap(entry -> Mono.fromCallable(() ->
                                    Customer.from(entry.getKey(), entry.getValue()) // Usar el método `from` sin modificar
                            ))
                            .map(customer -> new AccountResponse(
                                    customer.getId().getValue(),
                                    customer.getAccount().getId().getValue(),
                                    customer.getAccount().getAccountNumber().getValue(),
                                    customer.getAccount().getOwner().getValue(),
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getStatus().getValue()
                            ));
                });
    }




}
