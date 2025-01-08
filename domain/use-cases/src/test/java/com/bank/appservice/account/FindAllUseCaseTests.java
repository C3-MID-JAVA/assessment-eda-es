package com.bank.appservice.account;

import com.bank.account.Account;
import com.bank.appservice.usecases.account.GetAllAccountsUseCase;
import com.bank.gateway.IAccountRepository;
import com.bank.gateway.IBusMessage;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class FindAllUseCaseTests {
    @Test
    void findAllAccountsOK() {
        String id = UUID.randomUUID().toString();
        Account account = new Account(id, "test holder", BigDecimal.valueOf(7846));

        IAccountRepository accountRepositoryGateway = mock(IAccountRepository.class);
        IBusMessage busMessage = mock(IBusMessage.class);

        GetAllAccountsUseCase getAllAccountsUseCase = new GetAllAccountsUseCase(accountRepositoryGateway, busMessage);

        when(accountRepositoryGateway.findAllAccounts()).thenReturn(Flux.just(account));

        Flux<Account> result = getAllAccountsUseCase.apply();

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountRepositoryGateway, times(1)).findAllAccounts();
    }
}
