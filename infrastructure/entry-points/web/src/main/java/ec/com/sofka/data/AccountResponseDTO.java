package ec.com.sofka.data;

import java.math.BigDecimal;

public class AccountResponseDTO {
    public String id;

    public String accountNumber;

    public BigDecimal balance;

    public String owner;

    public AccountResponseDTO(String id, String accountNumber, BigDecimal balance, String owner) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
