package ec.com.sofka.aggregate.account.events;

import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {
    private  String accountNumber;
    private  BigDecimal accountBalance;
    private  String ownerName;
    private  String accountType;

    public AccountCreated(String accountNumber, BigDecimal accountBalance, String name, String accountType) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.ownerName = name;
        this.accountType = accountType;
    }

    public AccountCreated() {
        super(EventsEnum.ACCOUNT_CREATED.name());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAccountType() {
        return accountType;
    }
}
