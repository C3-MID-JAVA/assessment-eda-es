package ec.com.sofka.responses;

import ec.com.sofka.utils.enums.StatusEnum;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountResponse implements Serializable {

    private String number;
    private BigDecimal balance;
    private StatusEnum status;

    private UserResponse user;

    public AccountResponse(String number, BigDecimal balance, StatusEnum status, UserResponse user) {
        this.number = number;
        this.balance = balance;
        this.status = status;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

}