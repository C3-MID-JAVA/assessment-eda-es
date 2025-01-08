package ec.com.sofka.database.account;

import ec.com.sofka.data.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveMongoRepository<UserEntity, String> {

    Mono<UserEntity> save(UserEntity entity);

    Flux<UserEntity> findAll();

    Mono<UserEntity> findByIdentityCard(String identityCard);

    Mono<UserEntity> findById(String id);
}