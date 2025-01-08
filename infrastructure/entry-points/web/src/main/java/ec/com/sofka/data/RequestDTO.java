package ec.com.sofka.data;

import java.math.BigDecimal;

public class RequestDTO {
    public String customerId;
    //Name
    public String customer;
    //NumAcc
    public String account;
    public BigDecimal balance;
    public String  transactionType;





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


}
