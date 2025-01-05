package ec.com.sofka.entities.transactions;

import ec.com.sofka.entities.accountbank.AccountBank;
import ec.com.sofka.entities.transactions.values.objects.TransactionCost;
import ec.com.sofka.generics.utils.Entity;
import ec.com.sofka.entities.transactions.values.TransactionId;
import ec.com.sofka.entities.transactions.values.objects.Amount;
import ec.com.sofka.entities.transactions.values.objects.TransactionType;

import java.time.LocalDateTime;

public class Transactions extends Entity<TransactionId> {
    private final TransactionType transactionType;
    private final Amount amount;
    private final TransactionCost transactionCost;
    private final AccountBank accountBank;
    private LocalDateTime timestamp;

    public Transactions(TransactionId id,
                        TransactionType transactionType,
                        Amount amount,
                        TransactionCost transactionCost,
                        AccountBank accountBank
    ) {
        super(id);
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.timestamp = LocalDateTime.now();
        this.accountBank = accountBank;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Amount getAmount() {
        return amount;
    }

    public TransactionCost getTransactionCost() {
        return transactionCost;
    }

    public AccountBank getAccountBank() {
        return accountBank;
    }

}
