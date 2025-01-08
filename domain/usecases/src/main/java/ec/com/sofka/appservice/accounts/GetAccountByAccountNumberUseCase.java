package ec.com.sofka.appservice.accounts;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.data.response.AccountResponse;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Mono;

public class GetAccountByAccountNumberUseCase implements IUseCase <GetByElementRequest, AccountResponse> {

    private final IAccountRepository repository;
    private final IEventStore eventRepository;

    public GetAccountByAccountNumberUseCase(IAccountRepository repository, IEventStore eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<AccountResponse> execute(GetByElementRequest request) {
        // Obtener los eventos relacionados con el aggregateId de forma reactiva
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList() // Convierte el flujo de eventos en una lista
                .flatMap(events -> {
                    // Reconstruir el agregado usando los eventos
                    Customer customer = Customer.from(request.getAggregateId(), events);

                    // Obtener la cuenta del repositorio de forma reactiva
                    return repository.findByAccountNumber(customer.getAccount().getAccountNumber().getValue())
                            .map(accountDTO -> new AccountResponse(
                                    request.getAggregateId(),
                                    accountDTO.getAccountId(),
                                    accountDTO.getAccountNumber(),
                                    accountDTO.getName(),
                                    accountDTO.getBalance(),
                                    accountDTO.getStatus()
                            ));
                });
    }
/*

    @Override
    public Mono<GetAccountResponse> execute(GetAccountRequest request) {

        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .map(eventsList -> Customer.from(request.getAggregateId(), eventsList))
                .flatMap(accountAggregate ->
                        repository.findByAccountNumber(accountAggregate.getAccount().getAccountNumber().getValue())
                                .map(result -> new GetAccountResponse(
                                        request.getAggregateId(),
                                        result.getId(),
                                        result.getAccountNumber(),
                                        result.getName(),
                                        result.getBalance(),
                                        result.getStatus()
                                ))
                );
    }*/

}