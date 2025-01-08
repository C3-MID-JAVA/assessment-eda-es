package ec.com.sofka.aggregate.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.aggregate.entities.user.User;
import ec.com.sofka.utils.enums.EventsEnum;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreated extends DomainEvent {
    @JsonProperty(value = "accountId")
    private String accountId;
    private String accountNumber;
    private BigDecimal balance;
    private StatusEnum status;
    private User user;

    public AccountCreated(String accountId, String accountNumber, BigDecimal balance, StatusEnum status, User user) {
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.user = user;
    }

    public AccountCreated() {
        super(EventsEnum.ACCOUNT_CREATED.name());
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

}