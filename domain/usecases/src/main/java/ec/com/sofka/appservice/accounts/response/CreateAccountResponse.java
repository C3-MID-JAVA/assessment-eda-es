package ec.com.sofka.appservice.accounts.response;


import java.math.BigDecimal;

//Response class associated to the CreateAccountUseCase
public class CreateAccountResponse{
    private final String customerId;
    private final String accountNumber;
    private final String name;
    private final BigDecimal balance;

    public CreateAccountResponse(String customerId, String accountNumber, String name, BigDecimal balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }
    public BigDecimal getBalance() {
        return balance;
    }
}
