package ec.com.sofka.database;

import ec.com.sofka.data.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IMongoTransactionRepository extends ReactiveCrudRepository<TransactionEntity, String> {
}
