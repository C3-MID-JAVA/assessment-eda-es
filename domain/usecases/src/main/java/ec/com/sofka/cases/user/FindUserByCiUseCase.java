package ec.com.sofka.cases.user;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.generics.interfaces.IUseCaseGetElement;
import ec.com.sofka.mapper.UserMapper;
import ec.com.sofka.requests.GetElementRequest;
import ec.com.sofka.responses.UserResponse;
import reactor.core.publisher.Mono;

public class FindUserByCiUseCase implements IUseCaseGetElement<GetElementRequest, UserResponse> {
    private final UserRepository userRepository;
    private final IEventStore eventStore;

    public FindUserByCiUseCase(UserRepository userRepository, IEventStore eventStore) {
        this.userRepository = userRepository;
        this.eventStore = eventStore;
    }

    @Override
    public Mono<UserResponse> get(GetElementRequest request) {

        return eventStore.findAggregate(request.getAggregateId())
                .collectList()
                .map(eventsList -> AccountAggregate.from(request.getAggregateId(), eventsList))
                .flatMap(accountAggregate ->
                        userRepository.findByIdentityCard(accountAggregate.getUser().getIdentityCard().getValue())
                                .map(UserMapper::mapToResponseFromDTO)
                );

    }

}