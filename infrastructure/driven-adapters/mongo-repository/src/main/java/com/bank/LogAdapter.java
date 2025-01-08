package com.bank;

import com.bank.database.bank.ILogMongoRepository;
import com.bank.data.LogEntity;
import com.bank.gateway.ILogRepository;
import com.bank.mapper.LogEntityMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LogAdapter implements ILogRepository {
    private final ILogMongoRepository repository;

    public LogAdapter(ILogMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Log> create(String message) {
        return repository.insert(new LogEntity(message)).map(LogEntityMapper::toLog);
    }
}
