package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetAllAccountsUseCase implements IUseCaseGet<AccountResponse> {

    private final IEventStore eventRepository;

    public GetAllAccountsUseCase(IEventStore eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Flux<AccountResponse> get() {
        return eventRepository.findAllAggregates()
                .collectList()
                .flatMapMany(events -> {
                    Map<String, DomainEvent> latestEvents = events.stream()
                            .collect(Collectors.toMap(
                                    DomainEvent::getAggregateRootId,
                                    event -> event,
                                    (existing, replacement) -> existing.getVersion() >= replacement.getVersion() ? existing : replacement
                            ));

                    return Flux.fromIterable(latestEvents.values())
                            .map(event -> Customer.from(event.getAggregateRootId(), List.of(event)))
                            .map(customer -> new AccountResponse(
                                    customer.getId().getValue(),
                                    customer.getAccount().getId().getValue(),
                                    customer.getAccount().getNumber().getValue(),
                                    customer.getAccount().getName().getValue(),
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getStatus()
                            ));
                });
    }
}
