package ec.com.sofka.responses;

import java.io.Serializable;
import java.math.BigDecimal;


public class TransactionResponse implements Serializable {
    private BigDecimal amount;
    private String processingDate;
    private AccountResponse account;
    private String transactionType;
    private BigDecimal transactionCost;

    public TransactionResponse(BigDecimal amount, String processingDate, AccountResponse account, String transactionType, BigDecimal transactionCost) {
        this.amount = amount;
        this.processingDate = processingDate;
        this.account = account;
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

    public AccountResponse getAccount() {
        return account;
    }

    public void setAccount(AccountResponse account) {
        this.account = account;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(BigDecimal transactionCost) {
        this.transactionCost = transactionCost;
    }


}