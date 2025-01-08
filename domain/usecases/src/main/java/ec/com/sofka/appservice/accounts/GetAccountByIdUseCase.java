package ec.com.sofka.appservice.accounts;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.data.response.AccountResponse;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAccountByIdUseCase implements IUseCase<GetByElementRequest, AccountResponse> {

    private final IAccountRepository repository;
    private final IEventStore eventRepository;
    public GetAccountByIdUseCase(IAccountRepository repository, IEventStore eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<AccountResponse> execute(GetByElementRequest request) {

        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .map(eventsList -> {
                    if (eventsList.isEmpty()) {
                        throw new IllegalStateException("No events found for aggregate ID: " + request.getAggregateId());
                    }
                    return Customer.from(request.getAggregateId(), eventsList);
                })
                .flatMap(accountAggregate -> {
                    if (accountAggregate.getAccount() == null) {
                        return Mono.error(new IllegalStateException("Account is null for Customer aggregate"));
                    }
                    return repository.findById(accountAggregate.getAccount().getId().getValue())
                            .map(result -> new AccountResponse(
                                    request.getAggregateId(),
                                    result.getAccountId(),
                                    result.getAccountNumber(),
                                    result.getName(),
                                    result.getBalance(),
                                    result.getStatus()
                            ));
                });
    }

}
