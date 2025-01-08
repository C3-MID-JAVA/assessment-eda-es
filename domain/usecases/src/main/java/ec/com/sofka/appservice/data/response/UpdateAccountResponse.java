package ec.com.sofka.appservice.data.response;


import java.math.BigDecimal;

//Response class associated to the CreateAccountUseCase
public class UpdateAccountResponse {
    private String customerId;
    private String accountId;
    private String accountNumber;
    private String name;
    private BigDecimal balance;
    private String status;


    public UpdateAccountResponse(String customerId, String accountId, String accountNumber, String name, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.status = status;
    }

    public UpdateAccountResponse(String customerId, String accountId, String accountNumber, String name , String status, BigDecimal balance) {
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

    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }
}
