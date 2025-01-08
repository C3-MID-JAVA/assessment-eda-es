package com.bank.gateway.dto.mapper;

import com.bank.gateway.dto.OperationDTO;
import com.bank.operation.Operation;
import com.bank.operation.values.OperationId;
import com.bank.operation.values.objects.Type;
import com.bank.operation.values.objects.Value;

public class OperationDTOMapper {
    public static OperationDTO toDTO(Operation op) {
        return new OperationDTO(
                op.getId().getValue(),
                op.getValue().getValue(),
                op.getType().getValue(),
                op.getAccount()
        );
    }

    public static Operation toModel(OperationDTO dto) {
        return new Operation(
                OperationId.of(dto.getId()),
                Value.of(dto.getValue()),
                Type.of(dto.getType()),
                dto.getAccount()
        );
    }
}
