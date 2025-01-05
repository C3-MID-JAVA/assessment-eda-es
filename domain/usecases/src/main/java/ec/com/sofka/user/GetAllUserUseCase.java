package ec.com.sofka.user;

import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.user.request.EmptyRequest;
import ec.com.sofka.user.responses.UserResponse;
import reactor.core.publisher.Flux;

public class GetAllUserUseCase implements IUseCase<EmptyRequest, UserResponse> {
    private final UserRepository userRepository;

    public GetAllUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Flux<UserResponse> execute(EmptyRequest request) {
        return userRepository.getAll()
                .map(userDTO -> new UserResponse(
                                userDTO.getId(),
                                userDTO.getCustomerId(),
                                userDTO.getName(),
                                userDTO.getDocumentId()
                        )
                );
    }
}
