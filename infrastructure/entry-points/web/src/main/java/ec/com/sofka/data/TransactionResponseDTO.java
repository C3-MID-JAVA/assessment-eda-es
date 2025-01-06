package ec.com.sofka.data;

import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private String id;

    private String accountNumber;

    private TransactionType transactionType;

    private BigDecimal amount;

    private BigDecimal transactionCost;

    private BigDecimal newBalance;

    private LocalDateTime date;

    public TransactionResponseDTO(Transaction transaction, BigDecimal balance, String accountNumber) {
        this.id = transaction.getId();
        this.transactionType = transaction.getType();
        this.amount = transaction.getAmount();
        this.transactionCost = transaction.getTransactionCost();
        this.date = transaction.getDate();
        this.newBalance = balance;
        this.accountNumber = accountNumber;
    }

    public TransactionResponseDTO(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
