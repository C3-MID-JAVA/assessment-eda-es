package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {

    private String accountId;
    private final String accountNumber;
    private final BigDecimal accountBalance;
    private final String userId;

    public AccountCreated(String accountId, String accountNumber, BigDecimal accountBalance, String userId) {

        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.userId = userId;
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccountId() {
        return accountId;
    }

}
