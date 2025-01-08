package ec.com.sofka.cases.transaction;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.TransactionType;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.rules.BalanceCalculator;
import ec.com.sofka.rules.ValidateTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionUseCaseTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BalanceCalculator balanceCalculator;

    @Mock
    private ValidateTransaction validateTransaction;

    @InjectMocks
    private CreateTransactionUseCase createTransactionUseCase;

    private Account account;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setNumber("2200000000");
        account.setAvailableBalance(new BigDecimal(100));
        account.setRetainedBalance(new BigDecimal(0));
        account.setStatus("ACTIVE");

        TransactionType transactionType = new TransactionType();
        transactionType.setValue(new BigDecimal(100));
        transactionType.setTransactionCost(false);
        transactionType.setDiscount(true);
        
        transaction = new Transaction();
        transaction.setAccountNumber(account.getNumber());
        transaction.setDetails("transaction made in Manabí.");
        transaction.setDate(new Date());
        transaction.setValue(new BigDecimal(50));
        transaction.setStatus("ACTIVE");
        transaction.setAccount(account);
        transaction.setTransactionType(transactionType);
    }

    @Test
    void createTransaction_ShouldReturnTransaction() {
        when(validateTransaction.validateTransaction(transaction)).thenReturn(Mono.just(transaction));
        when(balanceCalculator.calculate(any(), any())).thenReturn(new BigDecimal("1000"));
        when(accountRepository.save(transaction.getAccount())).thenReturn(Mono.just(transaction.getAccount()));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = createTransactionUseCase.apply(transaction);

        StepVerifier.create(result)
                .expectNext(transaction)
                .expectComplete()
                .verify();

        verify(validateTransaction).validateTransaction(transaction);
        verify(balanceCalculator).calculate(any(), any());
        verify(accountRepository).save(transaction.getAccount());
        verify(transactionRepository).save(transaction);
    }

    @Test
    void updateBalanceAndSave_ShouldUpdateAccountBalanceAndSaveTransaction() {
        BigDecimal newBalance = new BigDecimal("900");
        when(validateTransaction.validateTransaction(transaction)).thenReturn(Mono.just(transaction));
        when(balanceCalculator.calculate(transaction, transaction.getAccount().getAvailableBalance())).thenReturn(newBalance);
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = createTransactionUseCase.apply(transaction);

        StepVerifier.create(result)
                .expectNext(transaction)
                .expectComplete()
                .verify();

        verify(accountRepository).save(account);
        verify(transactionRepository).save(transaction);
        assertEquals(newBalance, account.getAvailableBalance());
    }

}