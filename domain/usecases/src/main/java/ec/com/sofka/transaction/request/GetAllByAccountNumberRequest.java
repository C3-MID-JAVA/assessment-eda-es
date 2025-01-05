package ec.com.sofka.transaction.request;

import ec.com.sofka.generics.utils.Request;

public class GetAllByAccountNumberRequest extends Request {
    private final String accountNumber;

    public GetAllByAccountNumberRequest(String accountNumber) {
        super(null);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
