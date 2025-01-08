package ec.com.sofka.appservice.transactions.transactionprocess;

import ec.com.sofka.appservice.data.request.CreateAccountRequest;
import ec.com.sofka.appservice.data.request.CreateTransactionRequest;
import ec.com.sofka.appservice.data.response.AccountResponse;
import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.appservice.gateway.dto.AccountDTO;
import ec.com.sofka.appservice.gateway.dto.TransactionDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import ec.com.sofka.transaction.values.objects.TransactionDate;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class SaveTransactionUseCase implements IUseCase<CreateTransactionRequest, TransactionResponse>  {

    private final ITransactionRepository repository;

    public SaveTransactionUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<TransactionResponse> execute(CreateTransactionRequest  transaction) {
        TransactionDate transactionDate = TransactionDate.of(LocalDateTime.now());

        Transaction transactionWithDate = new Transaction(
                transaction.getAmount(),
                transaction.getTransactionCost(),
                transactionDate,
                transaction.getTransactionType(),
                transaction.getAccountId()
        );
        return repository.save(transactionWithDate);
    }

}
