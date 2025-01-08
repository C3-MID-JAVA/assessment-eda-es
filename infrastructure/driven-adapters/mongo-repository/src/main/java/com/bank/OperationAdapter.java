package com.bank;

import com.bank.database.bank.IOperationMongoRepository;
import com.bank.data.OperationEntity;
import com.bank.gateway.IOperationRepository;
import com.bank.gateway.dto.OperationDTO;
import com.bank.gateway.dto.mapper.OperationDTOMapper;
import com.bank.mapper.OperationEntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class OperationAdapter implements IOperationRepository {

    private final IOperationMongoRepository repository;
    private final ReactiveMongoTemplate bankMongoTemplate;

    public OperationAdapter(IOperationMongoRepository repository, @Qualifier("bankMongoTemplate") ReactiveMongoTemplate bankMongoTemplate) {
        this.repository = repository;
        this.bankMongoTemplate = bankMongoTemplate;
    }

    @Override
    public Flux<OperationDTO> findAllOperations() {
        return repository.findAll().map(OperationEntityMapper::toOperation).map(OperationDTOMapper::toDTO);
    }

    @Override
    public Mono<OperationDTO> findOperationById(String id) {
        return repository.findById(id).map(OperationEntityMapper::toOperation).map(OperationDTOMapper::toDTO);
    }

    @Override
    public Mono<OperationDTO> createOperation(OperationDTO dto) {
        var op = OperationEntityMapper.toOperationEntity(OperationDTOMapper.toModel(dto));
        return repository.save(op).map(OperationEntityMapper::toOperation).map(OperationDTOMapper::toDTO);
    }
}
