package ec.com.sofka.gateway.dto;

import java.math.BigDecimal;

public class TransactionDTO {
    private String id;

    private BigDecimal amount;

    private String processingDate;

    private AccountDTO accountDTO;

    private String transactionType;

    private  BigDecimal transactionCost;

    public TransactionDTO(){}

    public TransactionDTO(String id, String accountNumber, String details, BigDecimal amount, String processingDate, AccountDTO accountDTO, String transactionType, BigDecimal transactionCost) {
        this.id = id;
        this.amount = amount;
        this.processingDate = processingDate;
        this.accountDTO = accountDTO;
        this.transactionType = transactionType;
        this.transactionCost = transactionCost;
    }

    public TransactionDTO( String accountNumber, String details, BigDecimal amount, String processingDate, AccountDTO accountDTO, String transactionType, BigDecimal transactionCost) {
        this.amount = amount;
        this.processingDate = processingDate;
        this.accountDTO = accountDTO;
        this.transactionType = transactionType;
        this.transactionCost = transactionCost;
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

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String date) {
        this.processingDate = date;
    }

    public AccountDTO getAccount() {
        return accountDTO;
    }

    public void setAccount(AccountDTO accountDTOEntity) {
        this.accountDTO = accountDTOEntity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(BigDecimal transactionCost) {
        this.transactionCost = transactionCost;
    }




}