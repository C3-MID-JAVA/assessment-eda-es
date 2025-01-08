package com.bank.handler;

import com.bank.appservice.request.CreateOperationRequest;
import com.bank.appservice.request.GetOperationByIdRequest;
import com.bank.appservice.response.CreateOperationResponse;
import com.bank.appservice.response.GetOperationResponse;
import com.bank.appservice.usecases.account.GetAccountByIdUseCase;
import com.bank.appservice.usecases.operation.CreateOperationUseCase;
import com.bank.appservice.usecases.operation.GetAllOperationUseCase;
import com.bank.appservice.usecases.operation.GetOperationByIdUseCase;
import jakarta.validation.Valid;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Component
@Validated
public class OperationHandler {
    private final GetAllOperationUseCase getAllOperationUseCase;

    private final GetOperationByIdUseCase getOperationByIdUseCase;

    private final CreateOperationUseCase createOperationUseCase;

    private final GetAccountByIdUseCase getAccountByIdUseCase;

    public OperationHandler(
            GetAllOperationUseCase getAllOperationUseCase,
            GetOperationByIdUseCase getOperationByIdUseCase,
            CreateOperationUseCase createOperationUseCase,
            GetAccountByIdUseCase getAccountByIdUseCase
    ) {
        this.getAllOperationUseCase = getAllOperationUseCase;
        this.getOperationByIdUseCase = getOperationByIdUseCase;
        this.createOperationUseCase = createOperationUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
    }

    public Flux<GetOperationResponse> findAllOperations() {
        /*return getAllOperationUseCase.apply()
                .map(OperationMapper::toDTO)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No operations to list.")));*/
        return Flux.error(new NotImplementedException("Coming soon..."));
    }

    public Mono<GetOperationResponse> findOperationById(@Valid GetOperationByIdRequest request) {
        return getOperationByIdUseCase.execute(request)
                .switchIfEmpty(Mono.error(new NoSuchElementException(
                        "Operation with id " + request.getId() + " does not exist."
                )));
    }

    public Mono<CreateOperationResponse> createOperation(@Valid CreateOperationRequest request) {
        //todo: add logic later. need to apply the operation on the account

        return createOperationUseCase.execute(request)
                .switchIfEmpty(Mono.error(new RuntimeException("Could not create the operation")));
    }

    /*private Operation applyOperation(Operation op, Account acc) {
        Account account = op.getAccount();
        OperationType opType = Operation.getOperationTypeObj(op.getType().getValue());
        BigDecimal cost = opType.cost();
        BigDecimal value = op.getValue().getValue();

        BigDecimal operationTotal;
        switch (opType.action()) {
            case DEBIT -> operationTotal = value.add(cost).negate();
            case CREDIT -> operationTotal = value.subtract(cost);
            default -> operationTotal = BigDecimal.ZERO;
        }

        account.setBalance(account.getBalance().add(operationTotal));

        return op;
    }*/
}
