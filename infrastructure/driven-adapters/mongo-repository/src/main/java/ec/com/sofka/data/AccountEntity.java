package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "account")
public class AccountEntity {
    @Id
    private String id;

    @Field("account_id")
    private String accountId;

    @Field("accountNumber")
    private String accountNumber;

    @Field("owner")
    private String owner;

    @Field("balance")
    private BigDecimal balance;

    @Field("status_account")
    private String status;

    private String idUser;

    public AccountEntity(){
    }

    public AccountEntity(String id,String accountId,BigDecimal balance, String owner, String accountNumber, String idUser, String status) {
        this.id = id;
        this.accountId = accountId;
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.idUser=idUser;
        this.status = status;
    }

    public AccountEntity(String accountId,BigDecimal balance, String owner, String accountNumber, String idUser, String status) {
        this.accountId = accountId;
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.idUser=idUser;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    public String getOwner() {
        return owner;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }
}

