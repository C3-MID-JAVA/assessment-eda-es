package ec.com.sofka.requests;

import ec.com.sofka.generics.utils.AccountStatusEnum;
import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

//Usage of the Request class
public class AccountRequest extends Request {

    private  BigDecimal balance;
    private  String numberAcc;
    private  String customerName;
    private  String status;

    public AccountRequest(final String numberAcc, final String customerName, final BigDecimal balance) {
        super(null);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.customerName = customerName;
        this.status = AccountStatusEnum.ACCOUNT_ACTIVE.name();
    }



    public BigDecimal getBalance() {
        return balance;
    }

    public String getNumber() {
        return numberAcc;
    }

    public String getCustomerName() {
        return customerName;
    }

    public AccountStatusEnum getStatus() {
        return status;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setNumberAcc(String numberAcc) {
        this.numberAcc = numberAcc;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
