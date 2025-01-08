package ec.com.sofka.request;

import ec.com.sofka.generics.utils.Request;

public class GetCardByNumRequest extends Request {

    public GetCardByNumRequest(String aggregateId) {
        super(aggregateId);
    }

}
