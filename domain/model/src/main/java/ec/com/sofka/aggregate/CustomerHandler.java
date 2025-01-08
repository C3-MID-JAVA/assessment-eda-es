package ec.com.sofka.aggregate;

import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.*;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.generics.domain.DomainActionsContainer;

//6. Create the handler for the Aggregate root that will be on the Agreggate root constructor
public class CustomerHandler extends DomainActionsContainer {
    //7. Add the actions to the handler
    public CustomerHandler(Customer customer) {
        //8. Add the actions to the handler
        addDomainActions((AccountCreated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    Balance.of(event.getAccountBalance()),
                    NumberAcc.of(event.getAccountNumber()),
                    Name.of(event.getName()),
                    UserId.of(event.getUserId()),
                    Status.of(event.getStatus()));
            customer.setAccount(account);
        });

        addDomainActions((AccountUpdated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    Balance.of(event.getBalance()),
                    NumberAcc.of(event.getAccountNumber()),
                    Name.of(event.getName()),
                    UserId.of(event.getUserId()),
                    Status.of(event.getStatus()));
            customer.setAccount(account);
        });
    }
}
