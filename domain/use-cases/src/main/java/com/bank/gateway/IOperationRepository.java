package com.bank.gateway;

import com.bank.gateway.dto.OperationDTO;
import com.bank.operation.Operation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOperationRepository {
    Flux<OperationDTO> findAllOperations();
    Mono<OperationDTO> findOperationById(String id);
    Mono<OperationDTO> createOperation(OperationDTO operation);
}
