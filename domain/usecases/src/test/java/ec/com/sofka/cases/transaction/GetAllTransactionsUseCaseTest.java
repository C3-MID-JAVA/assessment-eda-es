package ec.com.sofka.cases.transaction;


import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllTransactionsUseCaseTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private GetAllTransactionsUseCase getAllTransactionsUseCase ;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setDetails("transaction made in Manabí.");
        transaction.setDate(new Date());
        transaction.setValue(new BigDecimal(50));
        transaction.setStatus("ACTIVE");
        transaction.setAccount(null);
        transaction.setTransactionType(null);
    }

    @Test
    public void testGetAllTransactionTypes_WhenTransactionTypesExist() {
        when(transactionRepository.findAll()).thenReturn(Flux.just(transaction));

        Flux<Transaction> result = getAllTransactionsUseCase.apply();

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();

        verify(transactionRepository).findAll();
    }

    @Test
    public void testGetAllTransactionTypes_WhenNoTransactionTypesExist() {
        when(transactionRepository.findAll()).thenReturn(Flux.empty());

        Flux<Transaction> result = getAllTransactionsUseCase.apply();

        StepVerifier.create(result)
                .expectNext()
                .verifyComplete();

        verify(transactionRepository).findAll();
    }

}