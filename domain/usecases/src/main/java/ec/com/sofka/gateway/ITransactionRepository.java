package ec.com.sofka.gateway;

import ec.com.sofka.aggregate.entities.transaction.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionRepository {
    Mono<Transaction> create(Transaction transaction);
    Flux<Transaction> getAllTransactions();
}
