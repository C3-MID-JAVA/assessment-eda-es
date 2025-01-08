package com.bank.appservice.operation;

import com.bank.account.Account;
import com.bank.appservice.usecases.operation.GetAllOperationUseCase;
import com.bank.operation.Operation;
import com.bank.operation.values.objects.OperationTypesEnum;
import com.bank.gateway.IBusMessage;
import com.bank.gateway.IOperationRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class FindAllUseCaseTests {
    @Test
    void findAllOperationsOK() {
        String id = UUID.randomUUID().toString();
        Operation operation = new Operation(id, BigDecimal.valueOf(875), OperationTypesEnum.ACCOUNT_DEPOSIT, new Account("123"));

        IOperationRepository operationRepositoryGateway = mock(IOperationRepository.class);
        IBusMessage busMessage = mock(IBusMessage.class);

        GetAllOperationUseCase getAllOperationUseCase = new GetAllOperationUseCase(operationRepositoryGateway, busMessage);

        when(operationRepositoryGateway.findAllOperations()).thenReturn(Flux.just(operation));

        Flux<Operation> result = getAllOperationUseCase.apply();

        StepVerifier.create(result)
                .expectNext(operation)
                .verifyComplete();

        verify(operationRepositoryGateway, times(1)).findAllOperations();
    }
}
