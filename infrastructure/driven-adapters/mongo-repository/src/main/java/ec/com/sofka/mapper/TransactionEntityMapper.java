package ec.com.sofka.mapper;

import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.aggregate.entities.transaction.values.TransactionId;
import ec.com.sofka.aggregate.entities.transaction.values.objects.Money;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.aggregate.entities.transaction.values.objects.TransactionType;
import ec.com.sofka.data.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityMapper {

    public static TransactionEntity mapToEntity(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionEntity(
                transaction.getId().getValue(),
                transaction.getAmount().getValue(),
                transaction.getProcessingDate().getValue(),
                AccountEntityMapper.mapToEntity(transaction.getAccount()),
                transaction.getTransactionType().getValue(),
                transaction.getTransactionCost().getValue()
        );
    }

    public static Transaction mapToModelFromEntity(TransactionEntity transactionEntity) {
        if (transactionEntity == null) {
            return null;
        }

        return new Transaction(
                TransactionId.of(transactionEntity.getId()),
                Money.of(transactionEntity.getAmount()),
                ProcessingDate.of(transactionEntity.getProcessingDate()),
                AccountEntityMapper.mapToModelFromEntity(transactionEntity.getAccount()),
                TransactionType.of(transactionEntity.getTransactionType()),
                Money.of(transactionEntity.getTransactionCost())
        );
    }
}
