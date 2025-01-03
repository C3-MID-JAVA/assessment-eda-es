package ec.com.sofka.aggregate;

import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.Balance;
import ec.com.sofka.account.values.objects.AccountNumber;
import ec.com.sofka.account.values.objects.UserId;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;

//6. Create the handler for the Aggregate root that will be on the Agreggate root constructor
public class CustomerHandler extends DomainActionsContainer {
    //7. Add the actions to the handler
    public CustomerHandler(Customer customer) {
        //8. Add the actions to the handler
        addDomainActions((AccountCreated event) -> {
            System.out.println("Procesando evento AccountCreated: " + event);
            Account account = new Account(new AccountId(),
                    Balance.of(event.getAccountBalance()),
                    AccountNumber.of(event.getAccountNumber()),
                    UserId.of(event.getUserId()));
            customer.setAccount(account);
        });
    }
}
