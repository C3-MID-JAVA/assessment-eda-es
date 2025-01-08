package ec.com.sofka.mapper;

import ec.com.sofka.appservice.data.response.AccountResponse;
import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionDTOMapper {

    public static TransactionResponseDTO toTransactionResponseDTO(TransactionResponse transactionResponse) {
        return new TransactionResponseDTO(
                transactionResponse.getTransactionId(),
                transactionResponse.getAccountId(),
                transactionResponse.getTransactionCost(),
                transactionResponse.getAmount(),
                transactionResponse.getTransactionDate(),
                transactionResponse.getTransactionType()
        );
    }

    public static TransactionResponseDTO toTransactionResponseDTOWithDefaults(TransactionResponse transactionResponse) {
        return new TransactionResponseDTO(
                transactionResponse.getTransactionId(),
                transactionResponse.getAccountId(),
                transactionResponse.getTransactionCost(),
                transactionResponse.getAmount(),
                transactionResponse.getTransactionDate(),
                transactionResponse.getTransactionType()
        );
    }

}
