package ec.com.sofka.config;

import ec.com.sofka.ConfigTest;
import ec.com.sofka.data.TransactionTypeEntity;
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

@ActiveProfiles("test")
@ContextConfiguration(classes = ConfigTest.class)
@AutoConfigureDataMongo
@DataMongoTest
public class ITransactionTypeRepositoryTest {
    private final ITransactionTypeRepository repository;

    @Autowired
    public ITransactionTypeRepositoryTest(ITransactionTypeRepository repository) {
        this.repository = repository;
    }
    @BeforeEach
    public void setUp() {
        repository.deleteAll().subscribe();
    }

    @Test
    public void TestSaveAndFindTransactionType() {
        TransactionTypeEntity transactionTypeEntity = new TransactionTypeEntity();
        transactionTypeEntity.setType("Deposit from branch");
        transactionTypeEntity.setDescription("Deposit from branch.");
        transactionTypeEntity.setTransactionCost(true);
        transactionTypeEntity.setDiscount(false);
        transactionTypeEntity.setValue(new BigDecimal(1));
        transactionTypeEntity.setStatus("ACTIVE");

        repository.save(transactionTypeEntity).subscribe();

        StepVerifier.create(repository.findAll())
                .expectNextMatches(item -> item.getType().equals(transactionTypeEntity.getType()))
                .expectComplete()
                .verify();
    }

    @Test
    public void TestDeleteTransactionType() {
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