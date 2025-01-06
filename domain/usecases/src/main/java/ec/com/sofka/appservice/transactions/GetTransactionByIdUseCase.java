package ec.com.sofka.appservice.transactions;

import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetTransactionByIdUseCase {

    private final ITransactionRepository repository;

    public GetTransactionByIdUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> apply(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No transaction found with id: " + id)));
    }
}
