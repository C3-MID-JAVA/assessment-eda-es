package ec.com.sofka.appservice.transactions.transactionprocess;

import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import ec.com.sofka.transaction.values.objects.TransactionDate;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class SaveTransactionUseCase {

    private final ITransactionRepository repository;

    public SaveTransactionUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> apply(Transaction transaction) {
        TransactionDate transactionDate = TransactionDate.of(LocalDateTime.now());

        Transaction transactionWithDate = new Transaction(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionCost(),
                transactionDate,
                transaction.getType(),
                transaction.getAccountId()
        );
        return repository.save(transaction);
    }
}
