package ec.com.sofka.responses.account;

import java.math.BigDecimal;

public class UpdateAccountResponse {
    private  String customerId;
    private String accountId;
    private String accountNumber;
    private String name;
    private  BigDecimal balance;
    private  String status;

    public UpdateAccountResponse(String customerId, String accountId, String accountNumber, String name, BigDecimal balance, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.status = status;
    }

    public UpdateAccountResponse(){

    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
