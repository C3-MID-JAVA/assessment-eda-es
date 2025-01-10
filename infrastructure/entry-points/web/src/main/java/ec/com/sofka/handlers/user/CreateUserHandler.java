package ec.com.sofka.handlers.user;

import ec.com.sofka.cases.user.CreateUserUseCase;
import ec.com.sofka.data.UserRequestDTO;
import ec.com.sofka.data.UserResponseDTO;
import ec.com.sofka.mapper.UserModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateUserHandler {
    private final CreateUserUseCase createUserUseCase;

    public CreateUserHandler(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public Mono<UserResponseDTO> save(UserRequestDTO user) {
        return createUserUseCase.execute(UserModelMapper.mapToRequest(user))
                .map(UserModelMapper::mapToDTO);
    }

}