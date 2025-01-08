package ec.com.sofka.account;

import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.*;
import ec.com.sofka.generics.utils.Entity;

import java.math.BigDecimal;

//4. Creation of an Entity class - They have logic and behavior, otherwise is a ValueObject.
public class Account extends Entity<AccountId> {
    private final Balance balance;
    private final NumberAcc numberAcc;
    private final Name name;
    private final UserId userId;
    private final Status status;

    public Account(AccountId id, Balance balance, NumberAcc numberAcc, Name name, UserId userId, Status status) {
        super(id);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.name = name;
        this.userId = userId;
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
    public UserId getUserId() {
        return userId;
    }
    public Status getStatus() {
        return status;
    }


}
