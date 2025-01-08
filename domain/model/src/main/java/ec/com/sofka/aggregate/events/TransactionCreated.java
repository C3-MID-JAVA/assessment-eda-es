package ec.com.sofka.aggregate.events;


import ec.com.sofka.aggregate.entities.transaction.values.objects.Money;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.aggregate.entities.transaction.values.objects.TransactionType;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.generics.utils.EventsEnum;


import java.math.BigDecimal;

public class TransactionCreated extends DomainEvent {
    private String transactionId;
    private BigDecimal amount;
    private String processingDate;
    private Account account;
    private String transactionType;
    private BigDecimal transactionCost;

    public TransactionCreated(String transactionId, BigDecimal amount, String processingDate, Account account, String transactionType, BigDecimal transactionCost) {
        super(EventsEnum.TRANSACTION_CREATED.name());
        this.transactionId = transactionId;
        this.amount = amount;
        this.processingDate = processingDate;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionCost = transactionCost;
    }

    public TransactionCreated(){
        super(EventsEnum.TRANSACTION_CREATED.name());
    }

    public String getTransactionId() {
        return transactionId;
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

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

}