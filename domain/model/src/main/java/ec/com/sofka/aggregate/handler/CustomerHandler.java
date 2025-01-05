package ec.com.sofka.aggregate.handler;

import ec.com.sofka.entities.account.Account;
import ec.com.sofka.entities.account.values.AccountBankId;
import ec.com.sofka.entities.account.values.objects.Balance;
import ec.com.sofka.entities.account.values.objects.Name;
import ec.com.sofka.entities.account.values.objects.NumberAcc;
import ec.com.sofka.aggregate.agregates.Customer;
import ec.com.sofka.aggregate.events.account.AccountCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;

//6. Create the handler for the Aggregate root that will be on the Agreggate root constructor
public class CustomerHandler extends DomainActionsContainer {
    //7. Add the actions to the handler
    public CustomerHandler(Customer customer) {
        //8. Add the actions to the handler
        addDomainActions((AccountCreated event) -> {
            Account account = new Account(new AccountBankId(),
                    Balance.of(event.getAccountBalance()),
                    NumberAcc.of(event.getAccountNumber()),
                    Name.of(event.getName()));
            customer.setAccount(account);
        });
    }
}
