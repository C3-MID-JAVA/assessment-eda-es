package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AccountStatusEnum;
import ec.com.sofka.generics.utils.EventsEnum;

import java.math.BigDecimal;

public class AccountUpdated extends DomainEvent {
    private String accountId;
    private String numberAcc;
    private String name;
    private AccountStatusEnum status;
    private BigDecimal balance;


    public AccountUpdated(String accountId,BigDecimal balance, String accountNumber, String name, AccountStatusEnum status) {
        super(EventsEnum.ACCOUNT_UPDATED.name());
        this.accountId = accountId;
        this.balance = balance;
        this.numberAcc = accountNumber;
        this.name = name;
        this.status = status;

    }

    public AccountUpdated() {
        super(EventsEnum.ACCOUNT_UPDATED.name());

    }

    public String getNumberAcc() {
        return numberAcc;
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public AccountStatusEnum getStatus() {
        return status;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
