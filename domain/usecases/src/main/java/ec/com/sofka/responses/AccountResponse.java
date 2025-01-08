package ec.com.sofka.responses;

import ec.com.sofka.generics.utils.AccountStatusEnum;

import java.math.BigDecimal;

//Response class associated to the CreateAccountUseCase
public class AccountResponse {
    private  String customerId;
    private  String accountId;
    private  String accountNumber;
    private  String name;
    private  BigDecimal balance;
    private  AccountStatusEnum status;


    public AccountResponse(String customerId, String accountId, String accountNumber, String name, BigDecimal balance, AccountStatusEnum status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
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

    public AccountStatusEnum getStatus() {
        return status;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setStatus(AccountStatusEnum status) {
        this.status = status;
    }
}
