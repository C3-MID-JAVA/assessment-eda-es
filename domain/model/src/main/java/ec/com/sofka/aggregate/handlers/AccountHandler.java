package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.aggregate.values.objects.Amount;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.aggregate.entities.account.values.AccountId;
import ec.com.sofka.aggregate.entities.account.values.objects.AccountNumber;

public class AccountHandler extends DomainActionsContainer {

    public AccountHandler(AccountAggregate accountAggregate) {
        addDomainActions((AccountCreated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    AccountNumber.of(event.getAccountNumber()),
                    Amount.of(event.getBalance()),
                    event.getStatus(),
                    event.getUser());

            accountAggregate.setAccount(account);
        });

        addDomainActions((AccountUpdated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    AccountNumber.of(event.getAccountNumber()),
                    Amount.of(event.getBalance()),
                    event.getStatus(),
                    event.getUser());

            accountAggregate.setAccount(account);
        });
    }

}