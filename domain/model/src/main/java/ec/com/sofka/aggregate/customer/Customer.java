package ec.com.sofka.aggregate.customer;

import ec.com.sofka.account.Account;
import ec.com.sofka.aggregate.customer.events.AccountBalanceUpdated;
import ec.com.sofka.aggregate.customer.events.AccountCreated;
import ec.com.sofka.aggregate.customer.events.UserCreated;
import ec.com.sofka.aggregate.customer.values.CustomerId;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AggregateRoot;
import ec.com.sofka.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class Customer extends AggregateRoot<CustomerId> {
    private Account account;
    private User user;

    public Customer() {
        super(new CustomerId());
        setSubscription(new CustomerHandler(this));
    }

    private Customer(final String id) {
        super(CustomerId.of(id));
        setSubscription(new CustomerHandler(this));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createAccount(BigDecimal balance, String accountNumber, String userId) {
        addEvent(new AccountCreated(accountNumber, balance, userId)).apply();
    }

    public void updateAccountBalance(BigDecimal balance, String accountNumber, String userId) {
        addEvent(new AccountBalanceUpdated(accountNumber, balance, userId)).apply();
    }

    public void createUser(String name, String documentId) {
        addEvent(new UserCreated(name, documentId)).apply();
    }

    public static Mono<Customer> from(final String id, Flux<DomainEvent> events) {
        Customer customer = new Customer(id);
        return events
                .flatMap(event -> Mono.fromRunnable(() -> customer.addEvent(event).apply()))
                .then(Mono.just(customer));
    }
}
