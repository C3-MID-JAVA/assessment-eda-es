package ec.com.sofka.requests;

import ec.com.sofka.generics.shared.Request;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

public class AccountRequest extends Request {
    private String accountNumber;
    private BigDecimal balance;
    private StatusEnum status;
    private String userId;
    private String userAggregateId;

    public AccountRequest(String aggregateId, String accountNumber, BigDecimal balance, StatusEnum status, String userId, String userAggregateId) {
        super(aggregateId);
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.userId = userId;
        this.userAggregateId = userAggregateId;
    }

    public AccountRequest(String accountNumber, BigDecimal balance, StatusEnum status, String userId, String userAggregateId) {
        super(null);
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.userId = userId;
        this.userAggregateId = userAggregateId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAggregateId() {
        return userAggregateId;
    }

    public void setUserAggregateId(String userAggregateId) {
        this.userAggregateId = userAggregateId;
    }
}