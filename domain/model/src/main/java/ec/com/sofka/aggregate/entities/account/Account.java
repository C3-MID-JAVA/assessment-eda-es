package ec.com.sofka.aggregate.entities.account;

import ec.com.sofka.aggregate.entities.account.values.AccountId;
import ec.com.sofka.aggregate.entities.account.values.objects.AccountNumber;
import ec.com.sofka.generics.shared.Entity;
import ec.com.sofka.aggregate.entities.user.User;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

public class Account extends Entity<AccountId> {
    private final AccountNumber accountNumber;
    private BigDecimal balance;
    private StatusEnum status;
    private User user;

    public Account(AccountId id, AccountNumber accountNumber, BigDecimal balance, StatusEnum status, User user ) {
        super(id);
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.user = user;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}