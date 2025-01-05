package ec.com.sofka.entities.accountbank.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class AccountNumber implements IValueObject<String> {
    private String value;
    public AccountNumber(String accountNumber) {
        this.value = accountNumber;
    }
    public static AccountNumber of(String accountNumber) {
        return new AccountNumber(accountNumber);
    }
    @Override
    public String getValue() {
        return value;
    }

    private String validate() {
        if(value == null){
            throw new IllegalArgumentException("The number can't be null");
        }

        if(value.isBlank()){
            throw new IllegalArgumentException("The number can't be empty");
        }

        if(value.length() != 9){
            throw new IllegalArgumentException("The number must have 9 characters");
        }

        if (!value.matches("[0-9]+")) {
            throw new IllegalArgumentException("The number must be numeric");
        }
        return value;
    }

}
