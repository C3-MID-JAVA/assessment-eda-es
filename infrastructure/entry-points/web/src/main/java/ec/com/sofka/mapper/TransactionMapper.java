package ec.com.sofka.mapper;

import ec.com.sofka.dto.TransactionRequestDTO;
import ec.com.sofka.dto.TransactionResponseDTO;
import ec.com.sofka.transaction.request.CreateTransactionRequest;

public class TransactionMapper {
    public static TransactionResponseDTO fromEntity(TransactionResponseDTO transactionResponse) {
        return new TransactionResponseDTO(
                transactionResponse.getOperationId(),
                transactionResponse.getFee(),
                transactionResponse.getNetAmount(),
                transactionResponse.getType(),
                transactionResponse.getTimestamp(),
                transactionResponse.getCustomerId()
        );
    }

    public static CreateTransactionRequest toEntity(TransactionRequestDTO transactionRequestDTO) {
        return new CreateTransactionRequest(
                transactionRequestDTO.getAmount(),
                transactionRequestDTO.getType(),
                transactionRequestDTO.getAccountNumber(),
                transactionRequestDTO.getCustomerId()
        );
    }
}
