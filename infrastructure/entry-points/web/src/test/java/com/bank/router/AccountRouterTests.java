package com.bank.router;

import com.bank.data.AccountRequestDTO;
import com.bank.data.AccountResponseDTO;
import com.bank.handler.AccountHandler;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountRouterTests {
    @Mock
    private AccountHandler accountHandler;
    @InjectMocks
    private AccountRouter accountRouter;
    @Mock
    private ServerRequest serverRequest;

    private AccountRequestDTO accountRequest;
    private AccountResponseDTO accountResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountRequest = new AccountRequestDTO("test holder", BigDecimal.ZERO);
        accountResponse = new AccountResponseDTO("1234", "test holder", BigDecimal.ZERO, new ArrayList<>());
    }

    @Test
    void accountCreateOK() {
        when(serverRequest.bodyToMono(AccountRequestDTO.class)).thenReturn(Mono.just(accountRequest));
        when(accountHandler.createAccount(accountRequest)).thenReturn(Mono.just(accountResponse));

        StepVerifier.create(accountRouter.createAccount(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.CREATED, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).createAccount(accountRequest);
    }

    @Test
    void getAccountByIdOK() {
        String accountId = "1234";

        when(accountHandler.findAccountById(accountId)).thenReturn(Mono.just(accountResponse));
        when(serverRequest.pathVariable("id")).thenReturn(accountId);

        StepVerifier.create(accountRouter.getAccountById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).findAccountById(accountId);
    }

    @Test
    void getAccountByIdFail() {
        String accountId = "4321";

        when(accountHandler.findAccountById(accountId)).thenReturn(Mono.empty());
        when(serverRequest.pathVariable("id")).thenReturn(accountId);

        StepVerifier.create(accountRouter.getAccountById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.NOT_FOUND, response.statusCode());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).findAccountById(accountId);
    }

    @Test
    void getAllAccountsOK() {
        when(accountHandler.findAllAccounts()).thenReturn(Flux.just(accountResponse));

        StepVerifier.create(accountRouter.getAllAccounts(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).findAllAccounts();
    }
}
