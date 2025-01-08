package ec.com.sofka.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountResponseDTO implements Serializable {

    private String accountNumber;
    private BigDecimal balance;
    private String status;
    private UserResponseDTO user;

    public AccountResponseDTO(String accountNumber, BigDecimal balance, String status, UserResponseDTO user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.user = user;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

}