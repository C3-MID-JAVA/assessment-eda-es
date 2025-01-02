package ec.com.sofka.responses;

import java.math.BigDecimal;

//Response class associated to the CreateAccountUseCase
public class CreateAccountResponse{
    private final String customerId;
    private final String accountNumber;
    private final BigDecimal balance;
    private final String userId;

    public CreateAccountResponse(String customerId, String accountNumber, BigDecimal balance, String userId) {
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
