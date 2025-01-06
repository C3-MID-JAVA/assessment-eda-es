package ec.com.sofka.appservice.transactions.transactionprocess;

import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class SaveTransactionUseCase {

    private final ITransactionRepository repository;

    public SaveTransactionUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> apply(Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        return repository.save(transaction);
    }
}
