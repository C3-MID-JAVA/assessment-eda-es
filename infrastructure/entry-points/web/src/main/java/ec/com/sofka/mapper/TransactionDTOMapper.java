package ec.com.sofka.mapper;

import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionDTOMapper {


    public static Transaction  transactionRequestToTransaction(TransactionRequestDTO transaction) {
        return new Transaction(
                null,
                transaction.getAmount(),
                null,
                null,
                transaction.getTransactionType(),
                transaction.getAccountNumber()
        );
    }

    public static TransactionResponseDTO transactionToTransactionResponse(Transaction transaction, BigDecimal balance, String accountNumber) {
        return new TransactionResponseDTO(
                transaction,
                balance,
                accountNumber
        );
    }

}
