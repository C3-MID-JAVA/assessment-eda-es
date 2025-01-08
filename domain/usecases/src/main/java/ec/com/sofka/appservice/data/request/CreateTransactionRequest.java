package ec.com.sofka.appservice.data.request;

import ec.com.sofka.enums.OperationType;
import ec.com.sofka.enums.TransactionType;
import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateTransactionRequest extends Request {
    private final BigDecimal transactionCost;
    private final BigDecimal amount;
    private final LocalDateTime transactionDate;
    private final TransactionType transactionType;
    private final String accountId;
    private final String accountNumber;

    public CreateTransactionRequest(final BigDecimal transactionCost,
                                    final BigDecimal amount,
                                    final LocalDateTime transactionDate,
                                    final TransactionType transactionType,
                                    final String accountId) {
        super(null);
        this.transactionCost = transactionCost;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.accountId = accountId;
        this.accountNumber = null;
    }

    public CreateTransactionRequest(final BigDecimal transactionCost,
                                    final BigDecimal amount,
                                    final LocalDateTime transactionDate,
                                    final String accountNumber,
                                    final TransactionType transactionType
                                    ) {
        super(null);
        this.transactionCost = transactionCost;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.accountId = null;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
