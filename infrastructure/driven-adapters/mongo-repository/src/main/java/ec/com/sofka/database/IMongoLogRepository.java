package ec.com.sofka.database;

import ec.com.sofka.data.LogEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IMongoLogRepository extends ReactiveMongoRepository<LogEntity, String> {
}
