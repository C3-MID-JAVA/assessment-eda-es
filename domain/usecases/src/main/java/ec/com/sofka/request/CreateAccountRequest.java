package ec.com.sofka.request;

import ec.com.sofka.generics.utils.Request;

//Usage of the Request class
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
