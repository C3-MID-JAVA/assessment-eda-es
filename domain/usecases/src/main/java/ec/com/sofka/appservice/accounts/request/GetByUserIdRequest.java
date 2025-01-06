package ec.com.sofka.appservice.accounts.request;

import ec.com.sofka.generics.utils.Request;

public class GetByUserIdRequest extends Request {

    private final String userId;

    public GetByUserIdRequest(String userId) {
        super(null);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
