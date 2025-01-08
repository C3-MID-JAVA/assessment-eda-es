package ec.com.sofka.mapper;

import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.aggregate.entities.transaction.values.TransactionId;
import ec.com.sofka.aggregate.entities.transaction.values.objects.Money;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.aggregate.entities.transaction.values.objects.TransactionType;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.responses.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    /**
     * Maps a TransactionDTO to a Transaction model.
     *
     * @param transactionDTO the DTO to map from
     * @return the mapped Transaction model
     */
    public static Transaction mapToModelFromDTO(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            return null;
        }

        return new Transaction(
                TransactionId.of(transactionDTO.getId()),
                Money.of(transactionDTO.getAmount()),
                ProcessingDate.of(transactionDTO.getProcessingDate()),
                AccountMapper.mapToModelFromDTO(transactionDTO.getAccount()),
                TransactionType.of(transactionDTO.getTransactionType()),
                Money.of(transactionDTO.getTransactionCost())
        );
    }

    /**
     * Maps a Transaction model to a TransactionDTO.
     *
     * @param transaction the model to map from
     * @return the mapped TransactionDTO
     */
    public static TransactionDTO mapToDTOFromModel(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionDTO(
                transaction.getId().getValue(),
                null, // Placeholder for details if needed
                transaction.getAmount().getValue(),
                transaction.getProcessingDate().getValue(),
                AccountMapper.mapToDTOFromModel(transaction.getAccount()),
                transaction.getTransactionType().getValue(),
                transaction.getTransactionCost().getValue()
        );
    }

    /**
     * Maps a Transaction model to a TransactionResponse.
     *
     * @param transaction the model to map from
     * @return the mapped TransactionResponse
     */
    public static TransactionResponse mapToResponseFromModel(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionResponse(
                transaction.getAmount().getValue(),
                transaction.getProcessingDate().getValue(),
                AccountMapper.mapToResponseFromModel(transaction.getAccount(), null),
                transaction.getTransactionType().getValue(),
                transaction.getTransactionCost().getValue()
        );
    }

    /**
     * Maps a TransactionDTO to a TransactionResponse.
     *
     * @param transactionDTO the DTO to map from
     * @return the mapped TransactionResponse
     */
    public static TransactionResponse mapToResponseFromDTO(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            return null;
        }

        return new TransactionResponse(
                transactionDTO.getAmount(),
                transactionDTO.getProcessingDate(),
                AccountMapper.mapToResponseFromDTO(transactionDTO.getAccount(), null),
                transactionDTO.getTransactionType(),
                transactionDTO.getTransactionCost()
        );
    }
}
