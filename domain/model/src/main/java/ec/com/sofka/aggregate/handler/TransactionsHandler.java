package ec.com.sofka.aggregate.handler;

import ec.com.sofka.aggregate.agregates.TransactionAgregate;
import ec.com.sofka.aggregate.events.Transaction.TransactionCreated;
import ec.com.sofka.aggregate.events.accountBank.AccountBankCreated;
import ec.com.sofka.entities.accountbank.AccountBank;
import ec.com.sofka.entities.transactions.Transactions;
import ec.com.sofka.entities.transactions.values.TransactionId;
import ec.com.sofka.entities.transactions.values.objects.Amount;
import ec.com.sofka.entities.transactions.values.objects.TransactionCost;
import ec.com.sofka.generics.domain.DomainActionsContainer;

public class TransactionsHandler extends DomainActionsContainer {

    public TransactionsHandler(TransactionAgregate accountBankAgregate) {
        addDomainActions((TransactionCreated event) -> {
            Transactions transaction = new Transactions(
                    new TransactionId(),
                    event.getType(),
                    Amount.of(event.getAmount()),
                    TransactionCost.of(event.getTransactionCost()),
                    event.getBankAccount() // cambiar estructura a use case que se necesite.
            );
        });
    }

}
