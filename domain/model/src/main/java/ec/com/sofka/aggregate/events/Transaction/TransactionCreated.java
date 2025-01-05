package ec.com.sofka.aggregate.events.Transaction;

import ec.com.sofka.aggregate.events.EventsEnum;
import ec.com.sofka.entities.accountbank.AccountBank;
import ec.com.sofka.entities.transactions.values.objects.Amount;
import ec.com.sofka.entities.transactions.values.objects.TransactionType;
import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionCreated extends DomainEvent {

    private TransactionType type; // "DEPOSIT", "WITHDRAWAL", "PURCHASE"
    private BigDecimal amount;
    private BigDecimal transactionCost;
    private LocalDateTime timestamp;
    private AccountBank bankAccount;

    public TransactionCreated(
            TransactionType type,
            BigDecimal amount,
            BigDecimal transactionCost,
            AccountBank bankAccount
    ) {
        super(EventsEnum.TRANSACTION_CREATED.name());
        this.type = type;
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.timestamp = LocalDateTime.now();
        this.bankAccount = bankAccount;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public AccountBank getBankAccount() {
        return bankAccount;
    }



}
