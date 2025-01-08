package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.aggregate.entities.account.values.AccountId;
import ec.com.sofka.aggregate.entities.account.values.objects.Balance;
import ec.com.sofka.aggregate.entities.account.values.objects.Name;
import ec.com.sofka.aggregate.entities.account.values.objects.NumberAcc;
import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.aggregate.entities.transaction.values.TransactionId;
import ec.com.sofka.aggregate.entities.transaction.values.objects.Money;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.aggregate.entities.transaction.values.objects.TransactionType;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;

//6. Create the handler for the Aggregate root that will be on the Agreggate root constructor
public class CustomerHandler extends DomainActionsContainer {
    //7. Add the actions to the handler
    public CustomerHandler(Customer customer) {
        //8. Add the actions to the handler


        addDomainActions((AccountCreated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    Balance.of(event.getBalance()),
                    NumberAcc.of(event.getNumberAcc()),
                    Name.of(event.getName()),
                    event.getStatus());
            customer.setAccount(account);
        });

        addDomainActions((AccountUpdated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    Balance.of(event.getBalance()),
                    NumberAcc.of(event.getNumberAcc()),
                    Name.of(event.getName()),
                    event.getStatus());
            customer.setAccount(account);
        });

            addDomainActions((TransactionCreated event) -> {
                Transaction transaction = new Transaction(
                        TransactionId.of(event.getTransactionId()),
                        Money.of(event.getAmount()),
                        ProcessingDate.of(event.getProcessingDate()),
                        event.getAccount(),
                        TransactionType.of(event.getTransactionType()),
                        Money.of(event.getTransactionCost()));

                customer.setTransaction(transaction);
            });

    }
}
