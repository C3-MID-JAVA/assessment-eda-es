package ec.com.sofka.transaction;

import ec.com.sofka.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private String id;

    private BigDecimal amount;

    private BigDecimal transactionCost;

    private LocalDateTime date;

    private TransactionType type;

    private String accountId;

    public Transaction(String id, BigDecimal amount, BigDecimal transactionCost, LocalDateTime date, TransactionType type, String accountId) {
        this.id = id;
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.date = date;
        this.type = type;
        this.accountId = accountId;
    }

    public Transaction(BigDecimal amount, BigDecimal transactionCost, LocalDateTime date, TransactionType type, String accountId) {
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.date = date;
        this.type = type;
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(BigDecimal transactionCost) {
        this.transactionCost = transactionCost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
