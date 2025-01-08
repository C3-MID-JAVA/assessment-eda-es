package ec.com.sofka;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import ec.com.sofka.responses.account.GetAccountResponse;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetAllAccountsUseCase implements IUseCaseGet<GetAccountResponse> {
    private final IEventStore eventRepository;

    public GetAllAccountsUseCase(IEventStore eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Flux<GetAccountResponse> get() {
        return eventRepository.findAllAggregates()
                .collectList()
                .flatMapMany(events -> {
                    Map<String, DomainEvent> mapLatestEvents = events.stream()
                            .collect(Collectors.toMap(
                                    DomainEvent::getAggregateRootId,
                                    event -> event,
                                    (existing, replacement) -> existing.getVersion() >= replacement.getVersion() ? existing : replacement
                            ));

                    List<DomainEvent> latestEvents = mapLatestEvents.values().stream().toList();

                    List<Customer> customers = latestEvents.stream()
                            .map(event -> Customer.from(event.getAggregateRootId(), latestEvents))
                            .toList();

                    return Flux.fromIterable(customers)
                            .map(customer -> new GetAccountResponse(
                                    customer.getId().getValue(),
                                    customer.getAccount().getId().getValue(),
                                    customer.getAccount().getNumber().getValue(),
                                    customer.getAccount().getName().getValue(),
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getStatus().getValue()
                            ));
                });
    }
}