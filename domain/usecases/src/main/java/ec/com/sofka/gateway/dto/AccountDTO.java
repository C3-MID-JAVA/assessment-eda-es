package ec.com.sofka.gateway.dto;

import java.math.BigDecimal;

public class AccountDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private String userId;
    private String customerId;

    public AccountDTO(String id) {
        this.id = id;
    }

    public AccountDTO(BigDecimal balance, String accountNumber, String userId, String customerId) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.customerId = customerId;
    }

    public AccountDTO(String id,String accountNumber,BigDecimal balance, String userId, String customerId) {
        this.accountNumber = accountNumber;
        this.id = id;
        this.balance = balance;
        this.userId = userId;
        this.customerId = customerId;
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

    public String getCustomerId() {
        return customerId;
    }
}
