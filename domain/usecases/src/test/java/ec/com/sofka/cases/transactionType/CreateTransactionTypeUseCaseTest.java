package ec.com.sofka.cases.transactionType;

import ec.com.sofka.TransactionType;
import ec.com.sofka.cases.transcationType.CreateTransactionTypeUseCase;
import ec.com.sofka.gateway.TransactionTypeRepository;
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
public class CreateTransactionTypeUseCaseTest {
    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @InjectMocks
    private CreateTransactionTypeUseCase createTransactionTypeUseCase;

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
    public void createTransactionType_ShouldReturnCreatedEntity_WhenTransactionTypeIsCreatedSuccessfully() {
        when(transactionTypeRepository.save(transactionType)).thenReturn(Mono.just(transactionType));

        Mono<TransactionType> result = createTransactionTypeUseCase.apply(transactionType);

        StepVerifier.create(result)
                .expectNext(transactionType)
                .verifyComplete();

        verify(transactionTypeRepository).save(transactionType);
    }

}