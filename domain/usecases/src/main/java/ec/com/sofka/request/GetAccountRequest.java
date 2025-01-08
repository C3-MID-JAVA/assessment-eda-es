package ec.com.sofka.request;

import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

public class GetAccountRequest extends Request {
    private final BigDecimal balance;
    private final String accountNumber;
    private final String ownerName;


    public GetAccountRequest(final String aggregateId, final String numberAcc, String accountNumber, String ownerName) {
        super(aggregateId);
        this.accountNumber = accountNumber;
        this.ownerName = null;
        this.balance = null;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
