package com.bank.database.bank;

import com.bank.Log;
import com.bank.data.LogEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ILogMongoRepository extends ReactiveMongoRepository<LogEntity, String> {}
