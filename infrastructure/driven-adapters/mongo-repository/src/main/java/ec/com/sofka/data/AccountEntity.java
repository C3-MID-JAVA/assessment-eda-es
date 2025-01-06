package ec.com.sofka.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "accounts")
public class AccountEntity {

    @Id
    private String id;

    private String accountNumber;

    private BigDecimal balance;

    private String userId;

    @Field(name = "aggregate_id")
    private String customerId;

    public AccountEntity() {
    }

    public AccountEntity(String accountNumber, BigDecimal balance, String userId, String customerId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
        this.customerId = customerId;
    }

    public AccountEntity(String id, String accountNumber, BigDecimal balance, String userId, String customerId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
