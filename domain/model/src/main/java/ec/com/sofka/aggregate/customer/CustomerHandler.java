package ec.com.sofka.aggregate.customer;

import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.Balance;
import ec.com.sofka.account.values.objects.AccountNumber;
import ec.com.sofka.aggregate.customer.events.AccountBalanceUpdated;
import ec.com.sofka.aggregate.customer.events.AccountCreated;
import ec.com.sofka.aggregate.customer.events.UserCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.user.User;
import ec.com.sofka.user.values.objects.DocumentId;
import ec.com.sofka.user.values.objects.Name;
import ec.com.sofka.user.values.UserId;

public class CustomerHandler extends DomainActionsContainer {
    public CustomerHandler(Customer customer) {

        addDomainActions((AccountCreated event) -> {
            Account account = new Account(new AccountId(),
                    Balance.of(event.getBalance()),
                    AccountNumber.of(event.getAccountNumber()),
                    UserId.of(event.getUserId()));
            customer.setAccount(account);
        });

        addDomainActions((AccountBalanceUpdated event) -> {
            Account account = new Account(new AccountId(),
                    Balance.of(event.getBalance()),
                    AccountNumber.of(event.getAccountNumber()),
                    UserId.of(event.getUserId()));
            customer.setAccount(account);
        });

        addDomainActions((UserCreated event) -> {
            User user = new User(new UserId(), Name.of(event.getName()), DocumentId.of(event.getDocumentId()));
            customer.setUser(user);
        });
    }
}
