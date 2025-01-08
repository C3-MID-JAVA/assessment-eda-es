package com.bank.data;

import com.bank.operation.values.objects.OperationAction;
import com.bank.operation.values.objects.OperationType;
import com.bank.operation.values.objects.OperationTypesEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "operations")
public class OperationEntity {
    @Id
    private String id;

    @Field("operation_value")
    private BigDecimal value;

    @Field("operation_type")
    private OperationTypesEnum type;

    @Field("operation_account")
    private AccountEntity account;

    public OperationEntity() {}

    public OperationEntity(String id, BigDecimal value, OperationTypesEnum type, AccountEntity account) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public OperationTypesEnum getType() {
        return type;
    }

    public void setType(OperationTypesEnum type) {
        this.type = type;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public static OperationType getOperationTypeObj(OperationTypesEnum type) {
        switch (type) {
            case ACCOUNT_DEPOSIT -> { return accountDepositOperation; }
            case ATM_DEPOSIT -> { return atmDepositOperation; }
            case ATM_EXTRACTION -> { return atmExtractionOperation; }
            case BRANCH_DEPOSIT -> { return branchDepositOperation; }
            case CARD_PHYSICAL_PURCHASE -> { return cardPhysicalPurchaseOperation; }
            case CARD_WEB_PURCHASE -> { return cardWebPurchaseOperation; }
        }

        throw new RuntimeException();
    }

    static OperationType accountDepositOperation =
            new OperationType(
                    OperationTypesEnum.ACCOUNT_DEPOSIT.name(),
                    BigDecimal.valueOf(1.5),
                    OperationAction.CREDIT
            );

    static OperationType atmDepositOperation =
            new OperationType(
                    OperationTypesEnum.ATM_DEPOSIT.name(),
                    BigDecimal.valueOf(2),
                    OperationAction.CREDIT
            );

    static OperationType atmExtractionOperation =
            new OperationType(
                    OperationTypesEnum.ATM_EXTRACTION.name(),
                    BigDecimal.valueOf(1),
                    OperationAction.DEBIT
            );

    static OperationType branchDepositOperation =
            new OperationType(
                    OperationTypesEnum.BRANCH_DEPOSIT.name(),
                    BigDecimal.valueOf(0),
                    OperationAction.CREDIT
            );

    static OperationType cardPhysicalPurchaseOperation =
            new OperationType(
                    OperationTypesEnum.CARD_PHYSICAL_PURCHASE.name(),
                    BigDecimal.valueOf(0),
                    OperationAction.DEBIT
            );

    static OperationType cardWebPurchaseOperation =
            new OperationType(
                    OperationTypesEnum.CARD_WEB_PURCHASE.name(),
                    BigDecimal.valueOf(5),
                    OperationAction.DEBIT
            );
}