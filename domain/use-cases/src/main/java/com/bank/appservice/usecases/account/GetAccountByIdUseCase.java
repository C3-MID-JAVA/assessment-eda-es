package com.bank.appservice.usecases.account;

import com.bank.aggregate.Customer;
import com.bank.appservice.request.GetAccountByIdRequest;
import com.bank.appservice.response.GetAccountResponse;
import com.bank.gateway.IAccountRepository;
import com.bank.gateway.IBusMessage;
import com.bank.gateway.IEventStore;
import com.bank.gateway.dto.AccountDTO;
import com.bank.generics.domain.DomainEvent;
import com.bank.generics.interfaces.IUseCaseExecute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetAccountByIdUseCase implements IUseCaseExecute<GetAccountByIdRequest, GetAccountResponse> {
    private final IAccountRepository repository;
    private final IBusMessage busMessage;
    private final IEventStore eventRepository;

    public GetAccountByIdUseCase(IAccountRepository repository, IBusMessage busMessage, IEventStore eventRepository) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<GetAccountResponse> execute(GetAccountByIdRequest request) {
        Flux<DomainEvent> event = eventRepository.findAggregate(request.getAggregateId());

        Customer customer = Customer.from(request.getAggregateId(), event.blockFirst());

        AccountDTO result = repository.findAccountById(customer.getAccount().getId().getValue())
                .switchIfEmpty(Mono.error(new NoSuchElementException()))
                .block();

        busMessage.sendMsg("Getting account by id " + result.getAccountId() + ".");

        return Mono.just(new GetAccountResponse(
                request.getAggregateId(),
                result.getAccountId(),
                result.getAccountNumber(),
                result.getHolder(),
                result.getBalance(),
                result.getActive()
        ));
    }
}
