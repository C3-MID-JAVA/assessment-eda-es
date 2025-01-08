package ec.com.sofka.cases.transaction;

import ec.com.sofka.gateway.ITransactionRepository;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.responses.TransactionResponse;
import reactor.core.publisher.Flux;

public class GetAllTransactionsUseCase {
    private final ITransactionRepository transactionRepository;

    public GetAllTransactionsUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<TransactionResponse> apply() {
        return transactionRepository.getAllTransactions()
                .map(TransactionMapper::mapToResponseFromModel); // Cambiado para trabajar con Transaction en lugar de TransactionDTO
    }
}
