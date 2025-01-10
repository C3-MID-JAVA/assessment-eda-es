package ec.com.sofka.cases.transaction;

import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.responses.TransactionResponse;
import reactor.core.publisher.Flux;

public class GetAllTransactionsUseCase {
    private final TransactionRepository transactionRepository;

    public GetAllTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<TransactionResponse> apply() {
        return transactionRepository.findAll()
                .map(TransactionMapper::mapToResponseFromDTO);
    }

}