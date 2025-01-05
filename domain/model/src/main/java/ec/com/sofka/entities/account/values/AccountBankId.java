package ec.com.sofka.entities.account.values;

import ec.com.sofka.generics.utils.Identity;

//3. Implementing generics: For account case, create the Identity
public class AccountBankId extends Identity {
    public AccountBankId() {
        super();
    }

    //wtf why private??
    private AccountBankId(final String id) {
        super(id);
    }

    //who tf are you?? I am the method to access/make instances the id with the private modifier :D
    public static AccountBankId of(final String id) {
        return new AccountBankId(id);
    }

}
