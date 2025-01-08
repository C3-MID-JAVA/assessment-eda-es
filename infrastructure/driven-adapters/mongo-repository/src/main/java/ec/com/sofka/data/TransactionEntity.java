package ec.com.sofka.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "transaction")
public class TransactionEntity {
    @Id
    private String id;

    @Field("transaction_id")
    private String transactionId;

    private BigDecimal amount;

    private String type;

    private BigDecimal cost;

    private String idAccount; // ID referenciado de la cuenta

    private String status;

    public TransactionEntity() {
    }

    public TransactionEntity(String id, String transactionId, BigDecimal amount, String type, BigDecimal cost, String idAccount, String status) {
        this.id = id;
        this.transactionId=transactionId;
        this.amount = amount;
        this.type = type;
        this.cost = cost;
        this.idAccount = idAccount;
        this.status = status;
    }
    public TransactionEntity( String transactionId, BigDecimal amount, String type, BigDecimal cost, String idAccount, String status) {
        this.transactionId=transactionId;
        this.amount = amount;
        this.type = type;
        this.cost = cost;
        this.idAccount = idAccount;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
