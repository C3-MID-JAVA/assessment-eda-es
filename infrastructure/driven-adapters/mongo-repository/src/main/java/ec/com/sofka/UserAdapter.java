package ec.com.sofka;

import ec.com.sofka.database.account.IUserRepository;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.gateway.dto.UserDTO;
import ec.com.sofka.mapper.UserEntityMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserAdapter implements UserRepository {
    private final IUserRepository userRepository;
    private final ReactiveMongoTemplate accountReactiveMongoTemplate;

    public UserAdapter(IUserRepository userRepository, ReactiveMongoTemplate accountReactiveMongoTemplate) {
        this.userRepository = userRepository;
        this.accountReactiveMongoTemplate = accountReactiveMongoTemplate;
    }

    @Override
    public Mono<UserDTO> save(UserDTO user) {
        return userRepository.save(UserEntityMapper.mapToEntity(user))
                .map(UserEntityMapper::mapToDTO);
    }

    @Override
    public Flux<UserDTO> findAll() {
        return userRepository.findAll()
                .map(UserEntityMapper::mapToDTO);
    }

    @Override
    public Mono<UserDTO> findById(String id) {
        return userRepository.findById(id)
                .map(UserEntityMapper::mapToDTO);
    }

    @Override
    public Mono<UserDTO> findByIdentityCard(String identityCard) {
        return userRepository.findByIdentityCard(identityCard)
                .map(UserEntityMapper::mapToDTO);
    }

}