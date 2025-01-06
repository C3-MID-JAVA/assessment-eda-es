package ec.com.sofka.adapter;

import ec.com.sofka.log.Log;
import ec.com.sofka.database.IMongoLogRepository;
import ec.com.sofka.data.LogEntity;
import ec.com.sofka.applogs.gateway.LogRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LogAdapter implements LogRepository {

    private final IMongoLogRepository repository;

    public LogAdapter(IMongoLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Void> create(Log log) {
        LogEntity logEntity = new LogEntity(log.getMessage(), log.getEntity(), log.getTimestamp());
        return repository.save(logEntity).then();
    }
}
