package ec.com.sofka.account.request;

import ec.com.sofka.account.values.AccountEnum;
import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

//Usage of the Request class
public class CreateAccountRequest extends Request {
    public CreateAccountRequest(String aggregateId) {
        super(aggregateId);
    }
}