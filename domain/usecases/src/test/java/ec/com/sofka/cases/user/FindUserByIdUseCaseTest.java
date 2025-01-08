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
public class FindUserByIdUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("u1");
        user.setCi("1310000000");
        user.setEmail("diego.loor@sofka.com.co");
        user.setFirstName("Diego");
        user.setLastName("Loor");
        user.setPassword("diego.loor@sofka.com.co");
        user.setStatus("ACTIVE");
    }

    @Test
    public void testFindUserById_WhenUserFound() {
        when(userRepository.findById(user.getId())).thenReturn(Mono.just(user));

        Mono<User> result = findUserByIdUseCase.apply(user.getId());

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).findById(user.getId());
    }

    @Test
    public void testFindUserById_WhenNoUserFound() {
        when(userRepository.findById(user.getId())).thenReturn(Mono.empty());

        Mono<User> result = findUserByIdUseCase.apply(user.getId());

        StepVerifier.create(result)
                .expectNext()
                .verifyComplete();

        verify(userRepository).findById(user.getId());
    }

}