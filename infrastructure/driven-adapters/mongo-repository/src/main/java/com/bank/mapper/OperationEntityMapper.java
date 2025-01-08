package com.bank.mapper;

import com.bank.account.Account;
import com.bank.operation.Operation;
import com.bank.data.AccountEntity;
import com.bank.data.OperationEntity;
import com.bank.operation.values.OperationId;
import com.bank.operation.values.objects.Type;
import com.bank.operation.values.objects.Value;

public class OperationEntityMapper {
    public static OperationEntity toOperationEntity(Operation op) {
        OperationEntity entity = new OperationEntity();

        entity.setId(op.getId().getValue());
        entity.setType(op.getType().getValue());
        entity.setValue(op.getValue().getValue());
        entity.setAccount(new AccountEntity(op.getAccount().getId().getValue()));

        return entity;
    }

    public static Operation toOperation(OperationEntity entity) {
        return new Operation(
                OperationId.of(entity.getId()),
                Value.of(entity.getValue()),
                Type.of(entity.getType()),
                AccountEntityMapper.toAccount(entity.getAccount())
        );
    }
}
