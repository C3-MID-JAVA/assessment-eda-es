package ec.com.sofka.account.request;

import ec.com.sofka.generics.utils.Request;

public class GetAccountByNumberRequest extends Request {

    private final String accountNumber;

    public GetAccountByNumberRequest(String accountNumber) {
        super(null);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
