package ec.com.sofka.gateway.dto;

import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

public class AccountDTO {
    private String id;

    private String accountNumber;

    private BigDecimal balance;

    private StatusEnum status;

    private UserDTO userDTO;

    public AccountDTO() {}

    public AccountDTO(String id, String accountNumber, BigDecimal balance, StatusEnum status, UserDTO userDTO) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.userDTO = userDTO;
    }

    public AccountDTO(String accountNumber, BigDecimal balance, StatusEnum status, UserDTO userDTO) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.userDTO = userDTO;
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

}