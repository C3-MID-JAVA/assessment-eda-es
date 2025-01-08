package ec.com.sofka.aggregate.operation;

import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.transaction.values.TransactionId;
import ec.com.sofka.transaction.values.objects.*;

public class TransactionHandler extends DomainActionsContainer {
    public TransactionHandler(TransactionAgregate operation) {

        addDomainActions((TransactionCreated event) -> {
            Transaction transaction = new Transaction(
                    TransactionId.of(event.getId()),
                    Amount.of(event.getAmount()),
                    Fee.of(event.getFee()),
                    NetAmount.of(event.getNetAmount()),
                    Timestamp.of(event.getTimestamp()),
                    Type.of(event.getType()),
                    AccountId.of(event.getAccountId())
            );
            operation.setTransaction(transaction);
        });
    }
}
