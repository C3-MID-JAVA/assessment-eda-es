package ec.com.sofka.account.request;

import ec.com.sofka.generics.utils.Request;

public class CreateAccountRequest extends Request {
    public CreateAccountRequest(String aggregateId) {
        super(aggregateId);
    }
}
