package ec.com.sofka.appservice.data.mapper;

import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.appservice.gateway.dto.TransactionDTO;

public class TransactionMapper {

    public static TransactionResponse toTransactionResponse(TransactionDTO transactionDTO) {
        return new TransactionResponse(
                transactionDTO.getTransactionId(), // TransactionId del DTO
                transactionDTO.getAccountId(),     // AccountId del DTO
                transactionDTO.getTransactionCost(), // TransactionCost del DTO
                transactionDTO.getAmount(),        // Amount del DTO
                transactionDTO.getDate(),          // Date del DTO
                transactionDTO.getType()           // TransactionType del DTO
        );
    }
}
