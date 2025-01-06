package ec.com.sofka.account.request;

import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

public class CreateAccountRequest extends Request
{

    private final String userId;
    private final String accountNumber;
    private final BigDecimal balance;

    public CreateAccountRequest(final String userId,String accountNumber,BigDecimal balance) {
        super(null);
        this.balance = balance;
        this.userId = userId;
        this.accountNumber = accountNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccountNumber() {return accountNumber;}

    public BigDecimal getBalance() {return balance;}

}
