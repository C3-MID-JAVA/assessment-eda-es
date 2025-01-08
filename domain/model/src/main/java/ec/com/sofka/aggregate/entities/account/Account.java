package ec.com.sofka.aggregate.entities.account;

import ec.com.sofka.aggregate.entities.account.values.AccountId;
import ec.com.sofka.aggregate.entities.account.values.objects.Balance;
import ec.com.sofka.aggregate.entities.account.values.objects.Name;
import ec.com.sofka.aggregate.entities.account.values.objects.NumberAcc;
import ec.com.sofka.generics.utils.AccountStatusEnum;
import ec.com.sofka.generics.utils.Entity;

//4. Creation of an Entity class - They have logic and behavior, otherwise is a ValueObject.
public class Account extends Entity<AccountId> {
    private final Balance balance;
    private final NumberAcc numberAcc;
    private final Name name;
    private final AccountStatusEnum status;

    public Account(AccountId id, Balance balance, NumberAcc numberAcc, Name name, AccountStatusEnum status) {
        super(id);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.name = name;
        this.status = status;
    }



    public Balance getBalance() {
        return balance;
    }

    public NumberAcc getNumber() {
        return numberAcc;
    }
    public Name getName() {
        return name;
    }

    public AccountStatusEnum getStatus() {
        return status;
    }


}
