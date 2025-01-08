package ec.com.sofka.handlers.transaction;

import ec.com.sofka.cases.transaction.GetAllTransactionsUseCase;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.mapper.TransactionModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllTransactionsHandler {
    private final GetAllTransactionsUseCase getAllTransactionsUseCase;

    public GetAllTransactionsHandler(GetAllTransactionsUseCase getAllTransactionsUseCase) {
        this.getAllTransactionsUseCase = getAllTransactionsUseCase;
    }

    public Flux<TransactionResponseDTO> getAllTransactions() {
        return getAllTransactionsUseCase.apply()
                .map(TransactionModelMapper::mapToDTO);
    }

}