package ec.com.sofka.appservice.transactions;

import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import reactor.core.publisher.Flux;

public class GetTransactionsUseCase {

    private final ITransactionRepository repository;

    public GetTransactionsUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Flux<Transaction> apply() {
        return repository.findAll();
    }
}
