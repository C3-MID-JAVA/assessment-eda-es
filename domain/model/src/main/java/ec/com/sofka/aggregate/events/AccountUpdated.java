package ec.com.sofka.aggregate.events;



import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountUpdated extends DomainEvent {
    private String accountId;
    private String accountNumber;
    private String name;
    private String status;
    private BigDecimal balance;
    private String userId;


    public AccountUpdated(String accountId,BigDecimal balance, String accountNumber, String name, String status, String userId) {
        super(EventsEnum.ACCOUNT_UPDATED.name());
        this.accountId = accountId;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.name = name;
        this.status = status;
        this.userId=userId;

    }

    public AccountUpdated() {
        super(EventsEnum.ACCOUNT_UPDATED.name());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public String getUserId() {
        return userId;
    }
}
