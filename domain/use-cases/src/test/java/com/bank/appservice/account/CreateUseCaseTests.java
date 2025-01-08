package com.bank.appservice.account;

import com.bank.account.Account;
import com.bank.appservice.usecases.account.CreateAccountUseCase;
import com.bank.gateway.IAccountRepository;
import com.bank.gateway.IBusMessage;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CreateUseCaseTests {
    @Test
    void createAccountOK() {
        String id = UUID.randomUUID().toString();
        Account account = new Account(id, "test holder", BigDecimal.valueOf(7846));

        IAccountRepository accountRepositoryGateway = mock(IAccountRepository.class);
        IBusMessage busMessage = mock(IBusMessage.class);

        CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountRepositoryGateway, busMessage);

        when(accountRepositoryGateway.createAccount(account)).thenReturn(Mono.just(account));

        Mono<Account> result = createAccountUseCase.apply(account);

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountRepositoryGateway, times(1)).createAccount(account);
    }
}
