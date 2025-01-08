package ec.com.sofka.gateway.dto;

import java.math.BigDecimal;

//This class is used to transfer data between the application and the database -
// Notice how this affect the AccountRepository interface that lives in usecases
//Notice also how this impacts on the driven adapter that implements the AccountRepository interface that lives in usecases.
public class AccountDTO {
    private String id;
    private String accountNumber;
    private String owner;
    private BigDecimal balance;
    private String idUser;
    private String status;


    public AccountDTO(BigDecimal balance, String owner, String accountNumber, String idUser, String status) {
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this .idUser = idUser;
        this.status = status;
    }

    public AccountDTO(String id, BigDecimal balance, String owner, String accountNumber, String idUser, String status) {
        this.id=id;
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this .idUser = idUser;
        this.status = status;
    }
    public AccountDTO(String id, BigDecimal balance, String owner, String accountNumber, String status) {
        this.id=id;
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.status = status;
    }

    public String getId() {
        return id;
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


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }
}
