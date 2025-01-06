package ec.com.sofka.database.transaction;

import ec.com.sofka.data.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IMongoTransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
}
