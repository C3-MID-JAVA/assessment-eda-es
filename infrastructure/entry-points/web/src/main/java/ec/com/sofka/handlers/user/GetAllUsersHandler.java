package ec.com.sofka.handlers.user;

import ec.com.sofka.cases.user.GetAllUsersUseCase;
import ec.com.sofka.data.UserResponseDTO;
import ec.com.sofka.mapper.UserModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllUsersHandler {
    private final GetAllUsersUseCase getAllUsersUseCase;

    public GetAllUsersHandler(GetAllUsersUseCase getAllUsersUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
    }

    public Flux<UserResponseDTO> getAllUsers() {
        return getAllUsersUseCase.apply()
                .map(UserModelMapper::mapToDTO);
    }

}