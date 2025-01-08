package ec.com.sofka.aggregate.entities.transaction;

import ec.com.sofka.aggregate.entities.transaction.values.objects.Money;
import ec.com.sofka.aggregate.entities.transaction.values.objects.TransactionType;
import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.aggregate.entities.transaction.values.TransactionId;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.generics.utils.Entity;

public class Transaction extends Entity<TransactionId> {

    private final Money amount;
    private final ProcessingDate processingDate;
    private final Account account; // entidad embebida
    private final TransactionType transactionType;
    private final Money transactionCost;

    public Transaction(TransactionId id, Money amount, ProcessingDate processingDate, Account account, TransactionType transactionType, Money transactionCost) {
        super(id);
        this.amount = amount;
        this.processingDate = processingDate;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionCost = transactionCost;
    }

    public Money getAmount() {
        return amount;
    }

    public ProcessingDate getProcessingDate() {
        return processingDate;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Money getTransactionCost() {
        return transactionCost;
    }
}