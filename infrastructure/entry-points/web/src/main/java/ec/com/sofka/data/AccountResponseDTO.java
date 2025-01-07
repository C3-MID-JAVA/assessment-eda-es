package ec.com.sofka.data;

import java.math.BigDecimal;

public class AccountResponseDTO {
    public String customerId;

    public String accountId;

    public String owner;

    public String accountNumber;

    public BigDecimal balance;

    public String status;

    public AccountResponseDTO(String customerId, String accountId, String owner, String accountNumber, BigDecimal balance, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }
}
