package com.bank.appservice.usecases.operation;

import com.bank.aggregate.Customer;
import com.bank.appservice.request.GetOperationByIdRequest;
import com.bank.appservice.response.GetOperationResponse;
import com.bank.gateway.IEventStore;
import com.bank.gateway.dto.OperationDTO;
import com.bank.generics.domain.DomainEvent;
import com.bank.generics.interfaces.IUseCaseExecute;
import com.bank.gateway.IBusMessage;
import com.bank.gateway.IOperationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetOperationByIdUseCase implements IUseCaseExecute<GetOperationByIdRequest, GetOperationResponse> {
    private final IOperationRepository repository;
    private final IBusMessage busMessage;
    private final IEventStore eventRepository;

    public GetOperationByIdUseCase(IOperationRepository repository, IBusMessage busMessage, IEventStore eventRepository) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<GetOperationResponse> execute(GetOperationByIdRequest request) {
        Flux<DomainEvent> event = eventRepository.findAggregate(request.getAggregateId());

        Customer customer = Customer.from(request.getAggregateId(), event.blockFirst());

        OperationDTO result = repository.findOperationById(customer.getOperation().getId().getValue())
                .switchIfEmpty(Mono.error(new NoSuchElementException()))
                .block();

        busMessage.sendMsg("Getting operation by id " + result.getId() + ".");

        return Mono.just(new GetOperationResponse(
                result.getId(),
                result.getValue(),
                result.getType(),
                result.getAccount()
        ));
    }
}
