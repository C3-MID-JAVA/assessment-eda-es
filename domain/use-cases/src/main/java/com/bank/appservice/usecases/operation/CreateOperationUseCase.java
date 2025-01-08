package com.bank.appservice.usecases.operation;

import com.bank.aggregate.Customer;
import com.bank.appservice.request.CreateOperationRequest;
import com.bank.appservice.response.CreateOperationResponse;
import com.bank.gateway.IEventStore;
import com.bank.gateway.dto.OperationDTO;
import com.bank.generics.interfaces.IUseCaseExecute;
import com.bank.gateway.IBusMessage;
import com.bank.gateway.IOperationRepository;
import reactor.core.publisher.Mono;

public class CreateOperationUseCase implements IUseCaseExecute<CreateOperationRequest, CreateOperationResponse> {
    private final IOperationRepository repository;
    private final IBusMessage busMessage;
    private final IEventStore eventRepository;

    public CreateOperationUseCase(IOperationRepository repository, IBusMessage busMessage, IEventStore eventRepository) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<CreateOperationResponse> execute(CreateOperationRequest request) {
        Customer customer = new Customer();

        customer.createOperation(request.getType(), request.getValue(), request.getAccount());

        repository.createOperation(
                new OperationDTO(
                        customer.getOperation().getValue().getValue(),
                        customer.getOperation().getType().getValue(),
                        customer.getOperation().getAccount()
                )
        );

        customer.getUncommittedEvents().forEach(eventRepository::save);

        customer.markEventsAsCommitted();

        busMessage.sendMsg("Creating " + request.getType().name() + " operation for $" + request.getValue() + " on account with id " + request.getAccount().getId() + ".");

        return Mono.just(new CreateOperationResponse(
                customer.getOperation().getId().getValue(),
                customer.getOperation().getValue().getValue(),
                customer.getOperation().getType().getValue(),
                customer.getOperation().getAccount()
        ));
    }
}
