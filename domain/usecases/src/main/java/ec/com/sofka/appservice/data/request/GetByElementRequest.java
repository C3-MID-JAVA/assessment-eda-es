package ec.com.sofka.appservice.data.request;

import ec.com.sofka.generics.utils.Request;

public class GetByElementRequest extends Request {
    private final String element;

    public GetByElementRequest(final String aggregateId,
                               final String element) {
        super(aggregateId);
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
