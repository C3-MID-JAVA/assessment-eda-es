package ec.com.sofka.transaction.request;

import ec.com.sofka.generics.utils.Request;
import ec.com.sofka.transaction.TransactionType;

import java.math.BigDecimal;

public class CreateTransactionRequest extends Request {
    private final BigDecimal amount;
    private final TransactionType type;
    private final String accountId;

    public CreateTransactionRequest(BigDecimal amount, TransactionType type, String accountId) {
        super(null);
        this.amount = amount;
        this.type = type;
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getAccountId() {
        return accountId;
    }
}
