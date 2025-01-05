package ec.com.sofka.mapper;

import ec.com.sofka.dto.TransactionRequestDTO;
import ec.com.sofka.dto.TransactionResponseDTO;
import ec.com.sofka.transaction.request.CreateTransactionRequest;
import ec.com.sofka.transaction.responses.TransactionResponse;

public class TransactionMapper {
    public static TransactionResponseDTO fromEntity(TransactionResponse transactionResponse) {
        return new TransactionResponseDTO(
                transactionResponse.getCustomerId(),
                transactionResponse.getAmount(),
                transactionResponse.getNetAmount(),
                transactionResponse.getType(),
                transactionResponse.getTimestamp()
        );
    }

    public static CreateTransactionRequest toEntity(TransactionRequestDTO transactionRequestDTO) {
        return new CreateTransactionRequest(transactionRequestDTO.getAmount(), transactionRequestDTO.getType(), transactionRequestDTO.getAccountNumber());
    }
}
