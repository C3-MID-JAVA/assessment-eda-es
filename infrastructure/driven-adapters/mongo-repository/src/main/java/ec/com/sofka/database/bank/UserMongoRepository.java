package ec.com.sofka.database.bank;

import ec.com.sofka.data.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMongoRepository extends ReactiveMongoRepository<UserEntity, String> {
}
