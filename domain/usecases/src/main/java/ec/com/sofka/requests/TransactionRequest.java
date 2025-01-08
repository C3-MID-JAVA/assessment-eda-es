package ec.com.sofka.requests;

import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;


public class TransactionRequest extends Request {
    private BigDecimal amount;
    private String processingDate;
    private String accountId;
    private String transactionType;
    private String transactionCost;


    public TransactionRequest(String aggregateId, BigDecimal amount, String processingDate, String accountId, String transactionType, String transactionCost) {
        super(aggregateId);
        this.amount = amount;
        this.processingDate = processingDate;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.transactionCost = transactionCost;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(String transactionCost) {
        this.transactionCost = transactionCost;
    }
}