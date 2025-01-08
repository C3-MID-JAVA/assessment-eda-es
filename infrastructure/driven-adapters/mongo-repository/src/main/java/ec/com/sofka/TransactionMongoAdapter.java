package ec.com.sofka;

import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.data.TransactionEntity;
import ec.com.sofka.database.IMongoTransactionRepository;
import ec.com.sofka.gateway.ITransactionRepository;
import ec.com.sofka.mapper.TransactionEntityMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionMongoAdapter implements ITransactionRepository {

    private final IMongoTransactionRepository repository;

    public TransactionMongoAdapter(IMongoTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Transaction> create(Transaction transaction) {
        return Mono.just(transaction)
                .map(TransactionEntityMapper::mapToEntity)
                .flatMap(repository::save)
                .map(TransactionEntityMapper::mapToModelFromEntity);
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        return repository.findAll()
                .map(TransactionEntityMapper::mapToModelFromEntity);
    }
}
