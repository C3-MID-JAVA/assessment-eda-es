package ec.com.sofka.aggregate.events.accountBank;

import ec.com.sofka.aggregate.events.EventsEnum;
import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountBankCreated extends DomainEvent {
    private final String accountHolder;
    private final String accountNumber;
    private final BigDecimal generalBalance;

    public AccountBankCreated(String accountHolder, String accountNumber, BigDecimal generalBalance) {
        super(EventsEnum.ACCOUNT_BANK_CREATED.name());
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.generalBalance = generalBalance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getGeneralBalance() {
        return generalBalance;
    }

}
