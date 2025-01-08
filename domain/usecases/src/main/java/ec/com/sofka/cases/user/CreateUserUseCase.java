package ec.com.sofka.cases.user;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.exception.ConflictException;
import ec.com.sofka.gateway.ErrorBusMessage;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.UserMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.requests.UserRequest;
import ec.com.sofka.responses.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateUserUseCase implements IUseCaseExecute<UserRequest, UserResponse> {
    private final IEventStore repository;
    private final UserRepository userRepository;
    private final ErrorBusMessage errorBusMessage;

    public CreateUserUseCase(IEventStore repository, UserRepository userRepository, ErrorBusMessage errorBusMessage) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.errorBusMessage = errorBusMessage;
    }

    public Mono<UserResponse> execute(UserRequest userRequest) {
        AccountAggregate accountAggregate = new AccountAggregate();

        return userRepository.findByIdentityCard(userRequest.getIdentityCard())
                .flatMap(userFound -> {
                    errorBusMessage.sendMsg(new ErrorMessage("User is already registered (" + userRequest.getIdentityCard() + ")",
                            "Create User"));
                    return Mono.<UserResponse>error(new ConflictException("The user is already registered."));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    accountAggregate.createUser(
                            userRequest.getFirstName(),
                            userRequest.getLastName(),
                            userRequest.getIdentityCard(),
                            userRequest.getEmail(),
                            userRequest.getPassword(),
                            userRequest.getStatus()
                    );

                    return userRepository.save(UserMapper.mapToDTOFromModel(accountAggregate.getUser()))
                            .flatMap(user -> Flux.fromIterable(accountAggregate.getUncommittedEvents())
                                    .flatMap(repository::save)
                                    .then(Mono.just(user)))
                            .then(Mono.fromCallable(() -> {
                                accountAggregate.markEventsAsCommitted();
                                return UserMapper.mapToResponseFromModel(accountAggregate.getUser());
                            }));
                }));
    }

}