package ec.com.sofka.appservice.transactions;

import ec.com.sofka.appservice.data.mapper.TransactionMapper;
import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.appservice.gateway.dto.TransactionDTO;
import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import reactor.core.publisher.Flux;

public class GetTransactionsUseCase {

    private final ITransactionRepository repository;

    public GetTransactionsUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Flux<TransactionResponse> apply() {
        return repository.findAll()
                .map(TransactionMapper::toTransactionResponse);
    }
}
