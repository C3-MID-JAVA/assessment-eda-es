package com.bank.database.events;

import com.bank.data.EventEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IEventMongoRepository extends ReactiveMongoRepository<EventEntity, String> {
    Flux<EventEntity> findByAggregateId(String aggregateId);
}
