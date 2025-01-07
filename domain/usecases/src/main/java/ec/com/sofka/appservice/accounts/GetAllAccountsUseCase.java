package ec.com.sofka.appservice.accounts;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.accounts.response.GetAccountResponse;
import ec.com.sofka.appservice.gateway.IBusMessage;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetAllAccountsUseCase implements IUseCaseGet<GetAccountResponse> {

    private final IAccountRepository repository;
    private final IBusMessage busMessage;

    private final IEventStore eventRepository;


    public GetAllAccountsUseCase(IAccountRepository repository, IBusMessage busMessage, IEventStore eventRepository) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.eventRepository = eventRepository;
    }
    @Override
    public Flux<GetAccountResponse> get() {
        // Obtener todos los eventos de manera reactiva
        return eventRepository.findAllAggregates() // Suponiendo que retorna Flux<DomainEvent>
                .collectList() // Agrupar todos los eventos en una lista reactiva
                .flatMapMany(events -> {
                    // Procesar los últimos eventos
                    Map<String, DomainEvent> mapLatestEvents = events.stream()
                            .collect(Collectors.toMap(
                                    DomainEvent::getAggregateRootId,   // Clave: aggregateId
                                    event -> event,                   // Valor: el evento en sí
                                    (existing, replacement) -> existing.getVersion() >= replacement.getVersion() ? existing : replacement // Conservar la última versión
                            ));

                    // Obtener la lista de los últimos eventos
                    Flux<DomainEvent> latestEventsFlux = Flux.fromIterable(mapLatestEvents.values());

                    // Reconstruir los clientes de manera reactiva
                    return latestEventsFlux
                            .flatMap(event -> Customer.from(event.getAggregateRootId(), Flux.just(event))) // Usar el evento de forma reactiva
                            .map(customer -> new GetAccountResponse(
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
