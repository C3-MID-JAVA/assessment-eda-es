package ec.com.sofka.cases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllAccountsUseCaseTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private GetAllAccountsUseCase getAllAccountsUseCase;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setNumber("2200000000");
        account.setAvailableBalance(new BigDecimal(100));
        account.setRetainedBalance(new BigDecimal(0));
        account.setStatus("ACTIVE");
    }

    @Test
    public void testGetAllAccounts_WhenAccountsExist() {
        when(accountRepository.findAll()).thenReturn(Flux.just(account));

        Flux<Account> result = getAllAccountsUseCase.apply();

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountRepository).findAll();
    }

    @Test
    public void testGetAllAccounts_WhenNoAccountsExist() {
        when(accountRepository.findAll()).thenReturn(Flux.empty());

        Flux<Account> result = getAllAccountsUseCase.apply();

        StepVerifier.create(result)
                .expectNext()
                .verifyComplete();

        verify(accountRepository).findAll();
    }

}