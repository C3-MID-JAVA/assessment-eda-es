package ec.com.sofka.request.account;

import ec.com.sofka.account.values.AccountEnum;
import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

//Usage of the Request class
public class CreateAccountRequest extends Request
{
    private final BigDecimal balance;
    private final String numberAcc;
    /// Owner
    private final String customerName;
    private final String  idUser;
    private final String status;

    public CreateAccountRequest(final BigDecimal balance, final String numberAcc, final String customerName, String idUser) {
        super(null);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.customerName = customerName;
        this.idUser = idUser;
        this.status = AccountEnum.ACCOUNT_ACTIVE.name();
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

    public String getIdUser() {
        return idUser;
    }

    public String getStatus() {
        return status;
    }

}
