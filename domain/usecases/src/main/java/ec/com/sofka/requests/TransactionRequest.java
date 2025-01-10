package ec.com.sofka.requests;

import ec.com.sofka.generics.shared.Request;

import java.math.BigDecimal;

public class TransactionRequest extends Request {

    private BigDecimal amount;
    private String processingDate;
    private String accountNumber;
    private String details;
    private String transactionTypeId;
    private String transactionTypeAggregateId;
    private String accountAggregateId;

    public TransactionRequest(BigDecimal amount, String processingDate, String accountNumber, String details, String transactionTypeId, String transactionTypeAggregateId, String accountAggregateId) {
        super(null);
        this.amount = amount;
        this.processingDate = processingDate;
        this.accountNumber = accountNumber;
        this.details = details;
        this.transactionTypeId = transactionTypeId;
        this.transactionTypeAggregateId = transactionTypeAggregateId;
        this.accountAggregateId = accountAggregateId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(String transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionTypeAggregateId() {
        return transactionTypeAggregateId;
    }

    public void setTransactionTypeAggregateId(String transactionTypeAggregateId) {
        this.transactionTypeAggregateId = transactionTypeAggregateId;
    }

    public String getAccountAggregateId() {
        return accountAggregateId;
    }

    public void setAccountAggregateId(String accountAggregateId) {
        this.accountAggregateId = accountAggregateId;
    }
}