package com.bank.operation;

import com.bank.account.Account;
import com.bank.generics.utils.Entity;
import com.bank.operation.values.objects.*;
import com.bank.operation.values.OperationId;

import java.math.BigDecimal;

public class Operation extends Entity<OperationId> {
    private Value value;
    private Type type;
    private Account account;

    public Operation(OperationId id, Value value, Type type, Account account) {
        super(id);
        this.value = value;
        this.type = type;
        this.account = account;
    }

    public Value getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public Account getAccount() {
        return account;
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