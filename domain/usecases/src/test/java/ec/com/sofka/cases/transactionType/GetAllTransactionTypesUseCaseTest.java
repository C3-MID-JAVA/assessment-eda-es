package ec.com.sofka.cases.transactionType;

import ec.com.sofka.TransactionType;
import ec.com.sofka.cases.transcationType.GetAllTransactionTypesUseCase;
import ec.com.sofka.gateway.TransactionTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllTransactionTypesUseCaseTest {
    @Mock
    private TransactionTypeRepository transactionTypeRepository;
    @InjectMocks
    private GetAllTransactionTypesUseCase getAllTransactionTypesUseCase;

    private TransactionType transactionType;

    @BeforeEach
    public void setUp() {
        transactionType = new TransactionType();
        transactionType.setType("Deposit from branch");
        transactionType.setDescription("Deposit from branch.");
        transactionType.setTransactionCost(true);
        transactionType.setDiscount(false);
        transactionType.setValue(new BigDecimal(1));
        transactionType.setStatus("ACTIVE");
    }

    @Test
    public void testGetAllTransactionTypes_WhenTransactionTypesExist() {
        when(transactionTypeRepository.findAll()).thenReturn(Flux.just(transactionType));

        Flux<TransactionType> result = getAllTransactionTypesUseCase.apply();

        StepVerifier.create(result)
                .expectNext(transactionType)
                .verifyComplete();

        verify(transactionTypeRepository).findAll();
    }

    @Test
    public void testGetAllTransactionTypes_WhenNoTransactionTypesExist() {
        when(transactionTypeRepository.findAll()).thenReturn(Flux.empty());

        Flux<TransactionType> result = getAllTransactionTypesUseCase.apply();

        StepVerifier.create(result)
                .expectNext()
                .verifyComplete();

        verify(transactionTypeRepository).findAll();
    }

}