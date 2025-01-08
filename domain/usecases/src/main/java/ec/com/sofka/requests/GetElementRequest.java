package ec.com.sofka.requests;

import ec.com.sofka.generics.utils.Request;

public class GetElementRequest extends Request {
    private final String element;

    public GetElementRequest(final String aggregateId, final String element) {
        super(aggregateId);
        this.element = element;
    }

    public String getElement() {
        return element;
    }

}