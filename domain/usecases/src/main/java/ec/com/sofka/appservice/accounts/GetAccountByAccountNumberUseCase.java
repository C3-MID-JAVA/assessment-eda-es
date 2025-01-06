package ec.com.sofka.appservice.accounts;

import ec.com.sofka.account.Account;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.accounts.request.GetAccountRequest;
import ec.com.sofka.appservice.accounts.response.GetAccountResponse;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Mono;

public class GetAccountByAccountNumberUseCase implements IUseCase <GetAccountRequest, GetAccountResponse> {

    private final IAccountRepository repository;
    private final IEventStore eventRepository;

    public GetAccountByAccountNumberUseCase(IAccountRepository repository, IEventStore eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<GetAccountResponse> execute(GetAccountRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    Customer customer = Customer.from(request.getAggregateId(), events);

                    if (customer.getAccount() == null || customer.getAccount().getAccountNumber() == null) {
                        return Mono.error(new IllegalStateException("Customer does not have a valid account"));
                    }

                    return repository.findByAccountNumber(customer.getAccount().getAccountNumber().getValue())
                            .map(result -> new GetAccountResponse(
                                    request.getAggregateId(),
                                    result.getId(),
                                    result.getAccountNumber(),
                                    result.getName(),
                                    result.getBalance(),
                                    result.getStatus()
                            ));
                });
    }

}