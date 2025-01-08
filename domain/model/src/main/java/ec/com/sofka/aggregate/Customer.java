package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.aggregate.entities.account.values.AccountId;
import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.aggregate.entities.transaction.values.TransactionId;
import ec.com.sofka.aggregate.entities.transaction.values.objects.Money;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.aggregate.entities.transaction.values.objects.TransactionType;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.aggregate.values.CustomerId;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AccountStatusEnum;
import ec.com.sofka.generics.utils.AggregateRoot;

import java.math.BigDecimal;
import java.util.List;

//2. Creation of the aggregate class - The communication between the entities and the external world.
public class Customer extends AggregateRoot<CustomerId> {
    //5. Add the Account to the aggregate: Can't be final bc the aggregate is mutable by EventDomains
    private Account account;
    private Transaction transaction;

    //To create the Aggregate the first time, ofc have to set the id as well.
    public Customer() {
        super(new CustomerId());
        //Add the handler to the aggregate
        setSubscription(new CustomerHandler(this));
    }

    //To rebuild the aggregate
    private Customer(final String id) {
        super(CustomerId.of(id));
        //Add the handler to the aggregate
        setSubscription(new CustomerHandler(this));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    //Remember that User as Aggregate is the open door to interact with the entities
    public void createAccount( BigDecimal balance, String numberAcc, String name, AccountStatusEnum status) {
        //Add the event to the aggregate
        addEvent(new AccountCreated(new AccountId().getValue(), numberAcc, balance ,name,status)).apply();

    }

    public void createTransaction(String transactionAccount, BigDecimal amount, String processingDate, Account account, String transactionType, BigDecimal transactionCost) {
        //Add the event to the aggregate
        addEvent(new TransactionCreated(new TransactionId().getValue(), amount, processingDate, account, transactionType, transactionCost)).apply();
    }

    //Remember that User as Aggregate is the open door to interact with the entities
    public void updateAccount(String accountId, BigDecimal balance, String numberAcc, String name, AccountStatusEnum status ) {
        //Add the event to the aggregate
        addEvent(new AccountUpdated(accountId, balance, numberAcc, name, status)).apply();

    }

    //To rebuild the aggregate
    public static Customer from(final String id, List<DomainEvent> events) {
        Customer customer = new Customer(id);
        events.forEach((event) -> customer.addEvent(event).apply());
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .forEach((event) -> customer.addEvent(event).apply());
        return customer;
    }
}
