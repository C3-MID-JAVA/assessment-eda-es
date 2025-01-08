package ec.com.sofka.appservice.data.response;

import ec.com.sofka.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private final String customerId;
    private final String transactionId;
    private final String accountId;
    private final String accountNumber;
    private final BigDecimal transactionCost;
    private final BigDecimal amount;
    private final LocalDateTime transactionDate;
    private final OperationType operationType;

    public TransactionResponse(String customerId, String transactionId,
                               String accountId,
                               BigDecimal transactionCost,
                               BigDecimal amount,
                               LocalDateTime transactionDate,
                               OperationType operationType) {
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionCost = transactionCost;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.operationType = operationType;
        this.accountNumber = null;
    }

    public TransactionResponse(String customerId, String transactionId,
                               BigDecimal transactionCost,
                               String accountNumber,
                               BigDecimal amount,
                               LocalDateTime transactionDate,
                               OperationType operationType) {
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionCost = transactionCost;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.operationType = operationType;
        this.accountId = null;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }
}
