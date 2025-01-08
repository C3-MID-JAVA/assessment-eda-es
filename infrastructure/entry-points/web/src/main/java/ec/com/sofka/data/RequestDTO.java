package ec.com.sofka.data;

import java.math.BigDecimal;

public class RequestDTO {
    public String customerId;
    //Name
    public String customer;
    //NumAcc
    public String account;

    public BigDecimal balance;

    public String idUser;

    public String status;

    public RequestDTO(String customerId, String customer, String account, BigDecimal balance, String idUser, String status) {
        this.customerId = customerId;
        this.customer = customer;
        this.account = account;
        this.balance = balance;
        this.idUser = idUser;
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getStatus() {
        return status;
    }

}
