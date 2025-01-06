package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountRetrieved extends DomainEvent {
    private final String accountNumber;
    private final BigDecimal accountBalance;
    private final String userId;

    public AccountRetrieved(String accountNumber, BigDecimal accountBalance, String userId) {
        super(EventsEnum.ACCOUNT_RETRIEVED.name());
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.userId = userId;
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
}
