package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AccountStatusEnum;
import ec.com.sofka.generics.utils.EventsEnum;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {
    private String accountId;
    private BigDecimal balance;
    private String numberAcc;
    private String name;
    private AccountStatusEnum status;


    public AccountCreated(String accountId, String accountNumber, BigDecimal balance, String name, AccountStatusEnum status) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountId = accountId;
        this.balance = balance;
        this.numberAcc = accountNumber;
        this.name = name;
        this.status = status;
    }

    public AccountCreated() {
        super(EventsEnum.ACCOUNT_CREATED.name());

    }

    public String getAccountId() {
        return accountId;
    }

    public String getNumberAcc() {
        return numberAcc;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public AccountStatusEnum getStatus() {
        return status;
    }


}
