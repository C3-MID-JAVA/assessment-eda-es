package ec.com.sofka.cases.user;

import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.mapper.UserMapper;
import ec.com.sofka.responses.UserResponse;
import reactor.core.publisher.Flux;

public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  Flux<UserResponse> apply() {
        return userRepository.findAll()
                .map(UserMapper::mapToResponseFromDTO);
    }

}