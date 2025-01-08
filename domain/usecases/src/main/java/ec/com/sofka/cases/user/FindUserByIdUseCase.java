package ec.com.sofka.cases.user;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.aggregate.entities.user.User;
import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.gateway.ErrorBusMessage;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.generics.interfaces.IUseCaseGetElement;
import ec.com.sofka.mapper.UserMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.requests.GetElementRequest;
import ec.com.sofka.responses.UserResponse;
import reactor.core.publisher.Mono;

public class FindUserByIdUseCase implements IUseCaseGetElement<GetElementRequest, UserResponse> {
    private final UserRepository userRepository;
    private final IEventStore eventStore;
    private final ErrorBusMessage errorBusMessage;

    public FindUserByIdUseCase(UserRepository userRepository, IEventStore eventStore, ErrorBusMessage errorBusMessage) {
        this.userRepository = userRepository;
        this.eventStore = eventStore;
        this.errorBusMessage = errorBusMessage;
    }

    @Override
    public Mono<UserResponse> get(GetElementRequest request) {
        return getUserByAggregate(request.getAggregateId())
                .map(UserMapper::mapToResponseFromModel);
    }

    public Mono<User> getUserByAggregate(String aggregateId) {
        return eventStore.findAggregate(aggregateId)
                .switchIfEmpty(Mono.defer(() -> {
                    errorBusMessage.sendMsg(new ErrorMessage("User not found", "Get User by Id"));
                    return Mono.error(new RecordNotFoundException("User not found."));
                }))
                .collectList()
                .map(eventsList -> AccountAggregate.from(aggregateId, eventsList))
                .flatMap(accountAggregate ->
                        userRepository.findById(accountAggregate.getUser().getId().getValue())
                                .map(UserMapper::mapToModelFromDTO)
                );
    }

}