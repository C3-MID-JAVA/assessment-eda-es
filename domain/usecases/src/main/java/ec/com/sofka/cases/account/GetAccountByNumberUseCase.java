package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.requests.GetElementRequest;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase implements IUseCaseExecute<GetElementRequest, Mono<AccountResponse>> {
    private final IAccountRepository accountRepository;
    private final IEventStore eventRepository;

    public GetAccountByNumberUseCase(IAccountRepository accountRepository, IEventStore eventRepository) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<AccountResponse> execute(GetElementRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    if (events.isEmpty()) {
                        return Mono.error(new NotFoundException("No events found for the given aggregate ID: " + request.getAggregateId()));
                    }

                    Customer customer = Customer.from(request.getAggregateId(), events);

                    return accountRepository.findByNumber(customer.getAccount().getNumber().getValue())
                            .switchIfEmpty(Mono.error(new NotFoundException("Account not found with the given number: " + customer.getAccount().getNumber().getValue())))
                            .map(account -> AccountMapper.mapToResponseFromDTO(account, request.getAggregateId()));
                });
    }
}
