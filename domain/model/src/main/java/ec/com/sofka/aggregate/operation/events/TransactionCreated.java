package ec.com.sofka.aggregate.operation.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionCreated extends DomainEvent {
    private final BigDecimal amount;
    private final BigDecimal fee;
    private final BigDecimal netAmount;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private final String accountId;

    public TransactionCreated(BigDecimal amount, BigDecimal fee, BigDecimal netAmount, TransactionType type, LocalDateTime timestamp, String accountId) {
        super(EventsEnum.TRANSACTION_CREATED.name());
        this.amount = amount;
        this.fee = fee;
        this.netAmount = netAmount;
        this.type = type;
        this.timestamp = timestamp;
        this.accountId = accountId;
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
