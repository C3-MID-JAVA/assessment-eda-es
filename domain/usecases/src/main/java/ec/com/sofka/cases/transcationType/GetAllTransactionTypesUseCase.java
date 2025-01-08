package ec.com.sofka.cases.transcationType;

import ec.com.sofka.gateway.TransactionTypeRepository;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.responses.TransactionTypeResponse;
import reactor.core.publisher.Flux;

public class GetAllTransactionTypesUseCase {
    private final TransactionTypeRepository transactionTypeRepository;

    public GetAllTransactionTypesUseCase(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public Flux<TransactionTypeResponse> apply() {
        return transactionTypeRepository.findAll()
                .map(TransactionTypeMapper::mapToResponseFromDTO);
    }

}