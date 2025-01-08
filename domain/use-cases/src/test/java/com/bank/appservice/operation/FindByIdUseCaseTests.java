package com.bank.appservice.operation;

import com.bank.account.Account;
import com.bank.appservice.usecases.operation.GetOperationByIdUseCase;
import com.bank.operation.Operation;
import com.bank.operation.values.objects.OperationTypesEnum;
import com.bank.gateway.IBusMessage;
import com.bank.gateway.IOperationRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class FindByIdUseCaseTests {
    @Test
    void findOperationByIdOK() {
        String id = UUID.randomUUID().toString();
        Operation operation = new Operation(id, BigDecimal.valueOf(875), OperationTypesEnum.ACCOUNT_DEPOSIT, new Account("123"));

        IOperationRepository operationRepositoryGateway = mock(IOperationRepository.class);
        IBusMessage busMessage = mock(IBusMessage.class);

        GetOperationByIdUseCase getOperationByIdUseCase = new GetOperationByIdUseCase(operationRepositoryGateway, busMessage);

        when(operationRepositoryGateway.findOperationById(id)).thenReturn(Mono.just(operation));

        Mono<Operation> result = getOperationByIdUseCase.apply(id);

        StepVerifier.create(result)
                .expectNext(operation)
                .verifyComplete();

        verify(operationRepositoryGateway, times(1)).findOperationById(id);
    }
}
