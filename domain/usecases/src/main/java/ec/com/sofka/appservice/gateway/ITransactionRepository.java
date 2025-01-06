package ec.com.sofka.appservice.gateway;

import ec.com.sofka.transaction.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionRepository {
    Flux<Transaction> findAll();
    Mono<Transaction> save(Transaction transaction);
    Mono<Transaction> findById(String id);
}
