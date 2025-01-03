package ec.com.sofka.account.request;

import ec.com.sofka.generics.utils.Request;

public class CreateAccountRequest extends Request
{
    private final String userId;

    public CreateAccountRequest(final String userId) {
        super(null);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
