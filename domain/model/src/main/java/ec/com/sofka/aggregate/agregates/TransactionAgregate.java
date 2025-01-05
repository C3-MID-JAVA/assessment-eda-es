package ec.com.sofka.aggregate.agregates;

import ec.com.sofka.aggregate.events.Transaction.TransactionCreated;
import ec.com.sofka.aggregate.handler.TransactionsHandler;
import ec.com.sofka.aggregate.values.TransactionId;
import ec.com.sofka.entities.accountbank.AccountBank;
import ec.com.sofka.entities.transactions.Transactions;
import ec.com.sofka.entities.transactions.values.objects.TransactionType;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionAgregate extends AggregateRoot<TransactionId> {

    private Transactions transactions;

    public TransactionAgregate(){
        super(new TransactionId());
        setSubscription(new TransactionsHandler(this));
    }

    private TransactionAgregate(final String id) {
        super(TransactionId.of(id));
        setSubscription(new TransactionsHandler(this));
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public void createTransaction(
            TransactionType type,
            BigDecimal amount,
            BigDecimal transactionCost,
            AccountBank bankAccount
            ) {
        addEvent( new TransactionCreated(
                type,
                amount,
                transactionCost,
                bankAccount
        ));
    }

    public static TransactionAgregate from(final String id, List<DomainEvent> events) {
        TransactionAgregate transactionAgregate = new TransactionAgregate(id);
        events.forEach(transactionAgregate::addEvent);
        return transactionAgregate;
    }

}
