package ec.com.sofka.request.account;

import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

public class UpdateAccountRequest  extends Request {
    private final BigDecimal balance;
    private final String numberAcc;
    /// Owner
    private final String customerName;
    private final String  idUser;
    private final String status;

    public UpdateAccountRequest(final String aggregateId, BigDecimal balance, String numberAcc, String customerName, String idUser, String status) {
        super(aggregateId);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.customerName = customerName;
        this.idUser = idUser;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public String getIdUser() {
        return idUser;
    }
}
