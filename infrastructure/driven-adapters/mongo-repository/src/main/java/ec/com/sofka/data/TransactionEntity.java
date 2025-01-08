package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "transactions")
public class TransactionEntity {

    @Id
    private String id;

    @Field("transaction_id")
    private String transactionId;

    @Field("amount")
    private BigDecimal amount;

    @Field("processing_date")
    private String processingDate;

    @Field("account")
    private AccountEntity account;

    @Field("transaction_type")
    private String transactionType;

    @Field("transaction_cost")
    private BigDecimal transactionCost;

    public TransactionEntity() {
    }

    public TransactionEntity(String transactionId, BigDecimal amount, String processingDate, AccountEntity account, String transactionType, BigDecimal transactionCost) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.processingDate = processingDate;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionCost = transactionCost;
    }

    public TransactionEntity(String id, String transactionId, BigDecimal amount, String processingDate, AccountEntity account, String transactionType, BigDecimal transactionCost) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.processingDate = processingDate;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionCost = transactionCost;
    }

    public String getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionCost(BigDecimal transactionCost) {
        this.transactionCost = transactionCost;
    }
}
