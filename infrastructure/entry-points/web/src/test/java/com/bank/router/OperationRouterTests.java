package com.bank.router;

import com.bank.operation.values.objects.OperationTypesEnum;
import com.bank.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OperationRouterTests {
    /*@Mock
    private OperationHandler operationHandler;
    @InjectMocks
    private OperationRouter operationRouter;
    @Mock
    private ServerRequest serverRequest;

    private OperationRequestDTO operationRequest;
    private OperationResponseDTO operationResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        operationRequest = new OperationRequestDTO(BigDecimal.valueOf(500), OperationTypesEnum.ACCOUNT_DEPOSIT, "27afdfd5-");
        operationResponse = new OperationResponseDTO("1234", BigDecimal.valueOf(500), OperationTypesEnum.ACCOUNT_DEPOSIT, "27afdfd5-");
    }

    @Test
    void operationCreateOK() {
        when(serverRequest.bodyToMono(OperationRequestDTO.class)).thenReturn(Mono.just(operationRequest));
        when(operationHandler.createOperation(operationRequest)).thenReturn(Mono.just(operationResponse));

        StepVerifier.create(operationRouter.createOperation(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.CREATED, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(operationHandler).createOperation(operationRequest);
    }

    @Test
    void getOperationByIdOK() {
        String operationId = "1234";

        when(operationHandler.findOperationById(operationId)).thenReturn(Mono.just(operationResponse));
        when(serverRequest.pathVariable("id")).thenReturn(operationId);

        StepVerifier.create(operationRouter.getOperationById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(operationHandler).findOperationById(operationId);
    }

    @Test
    void getOperationByIdFail() {
        String operationId = "4321";

        when(operationHandler.findOperationById(operationId)).thenReturn(Mono.empty());
        when(serverRequest.pathVariable("id")).thenReturn(operationId);

        StepVerifier.create(operationRouter.getOperationById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.NOT_FOUND, response.statusCode());
                    return true;
                })
                .verifyComplete();

        verify(operationHandler).findOperationById(operationId);
    }

    @Test
    void getAllOperationsOK() {
        when(operationHandler.findAllOperations()).thenReturn(Flux.just(operationResponse));

        StepVerifier.create(operationRouter.getAllOperations(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(operationHandler).findAllOperations();
    }*/
}
