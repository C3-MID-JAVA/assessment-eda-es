package ec.com.sofka.transaction.responses;

import ec.com.sofka.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private final String customerId;
    private final BigDecimal amount;
    private final BigDecimal fee;
    private final BigDecimal netAmount;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private final String accountId;

    public TransactionResponse(String customerId, BigDecimal amount, BigDecimal fee, BigDecimal netAmount, TransactionType type, LocalDateTime timestamp, String accountId) {
        this.customerId = customerId;
        this.amount = amount;
        this.fee = fee;
        this.netAmount = netAmount;
        this.type = type;
        this.timestamp = timestamp;
        this.accountId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getAccountId() {
        return accountId;
    }
}
