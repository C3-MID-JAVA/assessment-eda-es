package ec.com.sofka.cases.user;

import ec.com.sofka.User;
import ec.com.sofka.gateway.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setCi("1310000000");
        user.setEmail("diego.loor@sofka.com.co");
        user.setFirstName("Diego");
        user.setLastName("Loor");
        user.setPassword("diego.loor@sofka.com.co");
        user.setStatus("ACTIVE");
    }

    @Test
    public void createUser_ShouldReturnCreatedEntity_WhenUserIsCreatedSuccessfully() {
        when(userRepository.findByCi("1310000000")).thenReturn(Mono.empty());
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        Mono<User> result = createUserUseCase.apply(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).save(user);
    }

}