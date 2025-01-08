package ec.com.sofka.rules;


import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.TransactionType;
import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.exception.TransactionRejectedException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionTypeRepository;
import ec.com.sofka.rules.impl.ValidateTransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidateTransactionImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @InjectMocks
    private ValidateTransactionImpl validateTransactionImpl;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setAccountNumber("12345");
        transaction.setValue(new BigDecimal("100"));
    }

    @Test
    public void testValidateTransaction_Success() {
        Account account = new Account();
        account.setNumber("12345");
        account.setStatus("ACTIVE");
        account.setAvailableBalance(new BigDecimal("500"));

        TransactionType transactionType = new TransactionType();
        transactionType.setDiscount(false);
        transactionType.setValue(new BigDecimal("10"));

        when(accountRepository.findByNumber("12345")).thenReturn(Mono.just(account));
        when(transactionTypeRepository.findById("T001")).thenReturn(Mono.just(transactionType));

        StepVerifier.create(validateTransactionImpl.validateTransaction(transaction))
                .expectNext(transaction)
                .verifyComplete();

        verify(accountRepository).findByNumber("12345");
        verify(transactionTypeRepository).findById("T001");
    }

    @Test
    public void testValidateTransaction_AccountNotFound() {
        when(accountRepository.findByNumber("12345")).thenReturn(Mono.empty());
        when(transactionTypeRepository.findById("T001")).thenReturn(Mono.just(new TransactionType()));

        StepVerifier.create(validateTransactionImpl.validateTransaction(transaction))
                .expectError(RecordNotFoundException.class)
                .verify();

        verify(accountRepository).findByNumber("12345");
        verify(transactionTypeRepository).findById("T001");
    }

    @Test
    public void testValidateTransaction_TransactionTypeNotFound() {
        Account account = new Account();
        account.setNumber("12345");
        account.setStatus("ACTIVE");
        account.setAvailableBalance(new BigDecimal("500"));

        when(accountRepository.findByNumber("12345")).thenReturn(Mono.just(account));
        when(transactionTypeRepository.findById("T001")).thenReturn(Mono.empty());

        StepVerifier.create(validateTransactionImpl.validateTransaction(transaction))
                .expectError(RecordNotFoundException.class)
                .verify();

        verify(accountRepository).findByNumber("12345");
        verify(transactionTypeRepository).findById("T001");
    }

    @Test
    public void testValidateTransaction_AccountInactive() {
        Account account = new Account();
        account.setNumber("12345");
        account.setStatus("INACTIVE");
        account.setAvailableBalance(new BigDecimal("500"));

        TransactionType transactionType = new TransactionType();
        transactionType.setDiscount(false);
        transactionType.setValue(new BigDecimal("10"));

        when(accountRepository.findByNumber("12345")).thenReturn(Mono.just(account));
        when(transactionTypeRepository.findById("T001")).thenReturn(Mono.just(transactionType));

        StepVerifier.create(validateTransactionImpl.validateTransaction(transaction))
                .expectError(TransactionRejectedException.class)
                .verify();

        verify(accountRepository).findByNumber("12345");
        verify(transactionTypeRepository).findById("T001");
    }

    @Test
    public void testValidateTransaction_InsufficientFunds() {
        Account account = new Account();
        account.setNumber("12345");
        account.setStatus("ACTIVE");
        account.setAvailableBalance(new BigDecimal("50"));

        TransactionType transactionType = new TransactionType();
        transactionType.setDiscount(true);
        transactionType.setValue(new BigDecimal("10"));

        when(accountRepository.findByNumber("12345")).thenReturn(Mono.just(account));
        when(transactionTypeRepository.findById("T001")).thenReturn(Mono.just(transactionType));

        StepVerifier.create(validateTransactionImpl.validateTransaction(transaction))
                .expectError(TransactionRejectedException.class)
                .verify();

        verify(accountRepository).findByNumber("12345");
        verify(transactionTypeRepository).findById("T001");
    }
    
}