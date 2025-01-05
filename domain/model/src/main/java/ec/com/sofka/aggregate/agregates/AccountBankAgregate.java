package ec.com.sofka.aggregate.agregates;

import ec.com.sofka.aggregate.events.accountBank.AccountBankCreated;
import ec.com.sofka.entities.account.values.AccountBankId;
import ec.com.sofka.aggregate.handler.AccountBankHandler;
import ec.com.sofka.entities.accountbank.AccountBank;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AggregateRoot;

import java.math.BigDecimal;
import java.util.List;

public class AccountBankAgregate extends AggregateRoot<AccountBankId> {

    private AccountBank accountBank;

    public AccountBankAgregate() {
        super(new AccountBankId());
        setSubscription(new AccountBankHandler(this));
    }

    private AccountBankAgregate(final String id) {
        super(AccountBankId.of(id));
        setSubscription(new AccountBankHandler(this));
    }

    public AccountBank getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }

    public void createAccountBank(
            String accountHolder,
            String accountNumber,
            BigDecimal generalBalance

    ) {
        addEvent(new AccountBankCreated(
                accountHolder,
                accountNumber,
                generalBalance
        ));
    }

    public static AccountBankAgregate from(final String id, List<DomainEvent> events){
        AccountBankAgregate accountBankAgregate = new AccountBankAgregate(id);
        events.forEach(accountBankAgregate::addEvent);
        return accountBankAgregate;
    }

}
