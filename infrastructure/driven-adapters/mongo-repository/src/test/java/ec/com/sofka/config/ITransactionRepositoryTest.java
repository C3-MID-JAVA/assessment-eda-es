package ec.com.sofka.config;

import ec.com.sofka.ConfigTest;
import ec.com.sofka.data.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;

@ActiveProfiles("test")
@ContextConfiguration(classes = ConfigTest.class)
@AutoConfigureDataMongo
@DataMongoTest
public class ITransactionRepositoryTest {
    private final ITransactionRepository repository;

    @Autowired
    public ITransactionRepositoryTest(ITransactionRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setUp() {
        repository.deleteAll().subscribe();
    }

    @Test
    public void TestSaveAndFindTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setDetails("transaction made in Manabí.");
        transactionEntity.setProcessingDate(new Date());
        transactionEntity.setAmount(new BigDecimal(100));
        transactionEntity.setStatus("ACTIVE");
        transactionEntity.setAccount(null);
        transactionEntity.setTransactionTypeIdentify(null);

        repository.save(transactionEntity).subscribe();

        StepVerifier.create(repository.findAll())
                .expectNextMatches(item -> item.getDate().equals(transactionEntity.getProcessingDate()))
                .expectComplete()
                .verify();
    }

    @Test
    public void TestDeleteTransaction() {
        Mono<Void> deleteResult = repository.deleteAll();

        StepVerifier.create(deleteResult)
                .expectComplete()
                .verify();

        StepVerifier.create(repository.findAll())
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

}