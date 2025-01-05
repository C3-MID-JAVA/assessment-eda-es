package ec.com.sofka.dto;


import ec.com.sofka.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class TransactionResponseDTO {
    private String id;
    private BigDecimal fee;
    private BigDecimal netAmount;
    private TransactionType type;
    private LocalDateTime timestamp;

    public TransactionResponseDTO(String id, BigDecimal fee, BigDecimal netAmount, TransactionType type, LocalDateTime timestamp) {
        this.id = id;
        this.fee = fee;
        this.netAmount = netAmount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
