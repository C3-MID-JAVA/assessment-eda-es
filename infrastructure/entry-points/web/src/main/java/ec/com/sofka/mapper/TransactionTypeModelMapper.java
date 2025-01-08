package ec.com.sofka.mapper;

import ec.com.sofka.data.TransactionTypeRequestDTO;
import ec.com.sofka.data.TransactionTypeResponseDTO;
import ec.com.sofka.requests.TransactionTypeRequest;
import ec.com.sofka.responses.TransactionTypeResponse;
import ec.com.sofka.utils.enums.StatusEnum;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeModelMapper {

    public static TransactionTypeRequest mapToRequest(TransactionTypeRequestDTO transactionType) {
        if (transactionType == null) {
            return null;
        }

        return new TransactionTypeRequest(
                transactionType.getType(),
                transactionType.getDescription(),
                transactionType.getValue(),
                transactionType.getDiscount(),
                transactionType.getTransactionCost(),
                StatusEnum.valueOf(transactionType.getStatus()));

    }

    public static TransactionTypeResponseDTO mapToDTO(TransactionTypeResponse transactionType) {
        if (transactionType == null) {
            return null;
        }
        return new TransactionTypeResponseDTO(
                transactionType.getType(),
                transactionType.getDescription(),
                transactionType.getValue(),
                transactionType.getDiscount(),
                transactionType.getDiscount(),
                transactionType.getStatus().name()
        );
    }

}