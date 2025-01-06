package ec.com.sofka.account.responses;

import java.math.BigDecimal;

//Response class associated to the CreateAccountUseCase
public class AccountResponse {
    private final String customerId;
    private final String accountNumber;
    private final BigDecimal balance;
    private final String userId;

    public AccountResponse(String customerId, String accountNumber, BigDecimal balance, String userId) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getUserId() {
        return userId;
    }
}
