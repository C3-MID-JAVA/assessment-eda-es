package ec.com.sofka.gateway.dto;

import java.math.BigDecimal;

//This class is used to transfer data between the application and the database -
// Notice how this affect the AccountRepository interface that lives in usecases
//Notice also how this impacts on the driven adapter that implements the AccountRepository interface that lives in usecases.
public class AccountDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private String userId;

    public AccountDTO(String id) {
        this.id = id;
    }

    public AccountDTO(String id, BigDecimal balance, String userId, String accountNumber) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.userId = userId;
    }

    public AccountDTO(BigDecimal balance, String userId, String accountNumber) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    public String getUserId() {
        return userId;
    }


    public BigDecimal getBalance() {
        return balance;
    }
}
