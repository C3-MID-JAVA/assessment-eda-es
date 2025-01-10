package ec.com.sofka.handlers.transcationType;

import ec.com.sofka.cases.transcationType.GetAllTransactionTypesUseCase;
import ec.com.sofka.data.TransactionTypeResponseDTO;
import ec.com.sofka.mapper.TransactionTypeModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllTransactionTypesHandler {
    private final GetAllTransactionTypesUseCase getAllTransactionTypesUseCase;

    public GetAllTransactionTypesHandler(GetAllTransactionTypesUseCase getAllTransactionTypesUseCase) {
        this.getAllTransactionTypesUseCase = getAllTransactionTypesUseCase;
    }

    public Flux<TransactionTypeResponseDTO> getAllTransactionTypes() {
        return getAllTransactionTypesUseCase.apply()
                .map(TransactionTypeModelMapper::mapToDTO);
    }

}
