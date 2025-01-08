package ec.com.sofka.cases.transcationType;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.exception.ConflictException;
import ec.com.sofka.gateway.ErrorBusMessage;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.TransactionTypeRepository;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.requests.TransactionTypeRequest;
import ec.com.sofka.responses.TransactionTypeResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateTransactionTypeUseCase implements IUseCaseExecute<TransactionTypeRequest, TransactionTypeResponse> {
    private final IEventStore repository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final ErrorBusMessage errorBusMessage;

    public CreateTransactionTypeUseCase(IEventStore repository, TransactionTypeRepository transactionTypeRepository, ErrorBusMessage errorBusMessage) {
        this.repository = repository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.errorBusMessage = errorBusMessage;
    }

    @Override
    public Mono<TransactionTypeResponse> execute(TransactionTypeRequest transactionTypeRequest) {
        AccountAggregate accountAggregate = new AccountAggregate();

        return transactionTypeRepository.findByType(transactionTypeRequest.getType())
                .flatMap(typeFound -> {
                    errorBusMessage.sendMsg(new ErrorMessage("Transaction type is already registered (" + transactionTypeRequest.getType() + ")",
                            "Create TransactionType"));
                    return Mono.<TransactionTypeResponse>error(new ConflictException("The transaction type is already registered."));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    accountAggregate.createTransactionType(
                            transactionTypeRequest.getType(),
                            transactionTypeRequest.getDescription(),
                            transactionTypeRequest.getValue(),
                            transactionTypeRequest.getTransactionCost(),
                            transactionTypeRequest.getDiscount(),
                            transactionTypeRequest.getStatus()
                    );

                    return transactionTypeRepository.save(TransactionTypeMapper.mapToDTOFromModel(accountAggregate.getTransactionType()))
                            .flatMap(transactionType -> Flux.fromIterable(accountAggregate.getUncommittedEvents())
                                    .flatMap(repository::save)
                                    .then(Mono.just(transactionType)))
                            .then(Mono.fromCallable(() -> {
                                accountAggregate.markEventsAsCommitted();
                                return TransactionTypeMapper.mapToResponseFromModel(accountAggregate.getTransactionType());
                            }));

                }));
    }

}