package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountBalanceUpdated extends DomainEvent {

    private String accountId;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String name;
    private String status;

    public AccountBalanceUpdated(String eventType) {
        super(eventType);
    }


}
