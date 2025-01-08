package com.bank.database.bank;

import com.bank.data.AccountEntity;
import com.bank.gateway.dto.AccountDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountMongoRepository extends ReactiveMongoRepository<AccountEntity, String> {
    //Flux<AccountEntity> findAll();
    //Mono<AccountEntity> findById(String id);
    //Mono<AccountEntity> create(AccountDTO dto);
}