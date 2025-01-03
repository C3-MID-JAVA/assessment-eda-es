package ec.com.sofka.account.request;

import ec.com.sofka.generics.utils.Request;

public class GetAllByUserIdRequest extends Request {

    private final String userId;

    public GetAllByUserIdRequest(String userId) {
        super(null);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
