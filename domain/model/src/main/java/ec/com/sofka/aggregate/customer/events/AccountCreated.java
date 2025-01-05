package ec.com.sofka.aggregate.customer.events;

import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {
    private final String accountNumber;
    private final BigDecimal balance;
    private final String userId;

    public AccountCreated(String accountNumber, BigDecimal balance, String userId) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getUserId() {
        return userId;
    }
}
