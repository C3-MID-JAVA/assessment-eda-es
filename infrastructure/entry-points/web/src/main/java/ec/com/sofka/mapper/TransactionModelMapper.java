package ec.com.sofka.mapper;

import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.requests.TransactionRequest;
import ec.com.sofka.responses.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionModelMapper {

    public static TransactionRequest mapToRequest(TransactionRequestDTO transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionRequest(
                transaction.getAmount(),
                transaction.getProcessingDate(),
                transaction.getDetails(),
                transaction.getAccountNumber(),
                transaction.getTransactionTypeId(),
                transaction.getTransactionTypeAggregateId(),
                transaction.getAccountAggregateId()
        );

    }

    public static TransactionResponseDTO mapToDTO(TransactionResponse transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionResponseDTO(
                transaction.getAccountNumber(),
                transaction.getDetails(),
                transaction.getAmount(),
                transaction.getProcessingDate(),
                AccountModelMapper.mapToDTO(transaction.getAccount()),
                TransactionTypeModelMapper.mapToDTO(transaction.getTransactionType())
        );
    }

}