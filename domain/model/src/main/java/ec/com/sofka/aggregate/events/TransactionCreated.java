package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.aggregate.entities.transactionType.TransactionType;
import ec.com.sofka.utils.enums.EventsEnum;

import java.math.BigDecimal;

public class TransactionCreated extends DomainEvent {
    private String transactionId;
    private String transactionAccount;
    private String details;
    private BigDecimal amount;
    private String processingDate;
    private Account account;
    private TransactionType transactionType;

    public TransactionCreated(String transactionId, String transactionAccount, String details, BigDecimal amount, String processingDate, Account account, TransactionType transactionType) {
        super(EventsEnum.TRANSACTION_CREATED.name());
        this.transactionId = transactionId;
        this.transactionAccount = transactionAccount;
        this.details = details;
        this.amount = amount;
        this.processingDate = processingDate;
        this.account = account;
        this.transactionType = transactionType;
    }

    public TransactionCreated(){
        super(EventsEnum.TRANSACTION_CREATED.name());
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionAccount() {
        return transactionAccount;
    }

    public String getDetails() {
        return details;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

}