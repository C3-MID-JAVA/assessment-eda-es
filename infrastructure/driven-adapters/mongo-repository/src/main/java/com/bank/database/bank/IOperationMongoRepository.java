package com.bank.database.bank;

import com.bank.data.OperationEntity;
import com.bank.gateway.dto.OperationDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOperationMongoRepository extends ReactiveMongoRepository<OperationEntity, String> {
    //Flux<OperationEntity> findAll();
    //Mono<OperationEntity> findById(String id);
    //Mono<OperationEntity> create(OperationDTO operation);
}