package ec.com.sofka.user.request;

import ec.com.sofka.generics.utils.Request;

public class EmptyRequest extends Request {
    public static final EmptyRequest INSTANCE = new EmptyRequest();

    private EmptyRequest() {
        super(null);
    }
}
