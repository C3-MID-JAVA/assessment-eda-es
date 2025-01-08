package ec.com.sofka.data;

import java.math.BigDecimal;

public class ResponseDTO {
    public String customerId;
    public String accountId;
    public String accountName;
    public String status;
    public BigDecimal balance;
    public String numberAcc;

    public ResponseDTO(String customerId, String accountId, String accountName, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.status = status;
    }

    public ResponseDTO(String customerId, String accountId, String accountName, String status, BigDecimal balance, String numberAcc ) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.status = status;
        this.balance=balance;
        this.numberAcc=numberAcc;
    }



    public String getAccountId() {
        return accountId;
    }

    public String getStatus() {
        return status;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getNumberAcc() {
        return numberAcc;
    }

}
