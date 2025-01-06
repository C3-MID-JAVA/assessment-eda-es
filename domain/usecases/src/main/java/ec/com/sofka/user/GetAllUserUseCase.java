package ec.com.sofka.user;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.user.request.EmptyRequest;
import ec.com.sofka.user.responses.UserResponse;
import reactor.core.publisher.Flux;

public class GetAllUserUseCase implements IUseCase<EmptyRequest, UserResponse> {
    private final IEventStore repository;
    private final UserRepository userRepository;

    public GetAllUserUseCase(IEventStore repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Flux<UserResponse> execute(EmptyRequest request) {
        return userRepository.getAll()
                .flatMap(userDTO -> repository.findAggregate(userDTO.getCustomerId())
                        .collectList() // Recopila los eventos como una lista
                        .flatMapMany(events -> {
                            // Crea el agregado Customer usando la lista de eventos
                            Customer customer = Customer.from(userDTO.getCustomerId(), events);

                            // Lógica del agregado
                            customer.retrieveUser(userDTO.getName(), userDTO.getDocumentId());

                            // Procesa los eventos no comprometidos
                            return Flux.fromIterable(customer.getUncommittedEvents())
                                    .flatMap(repository::save)
                                    .doOnTerminate(customer::markEventsAsCommitted)
                                    .thenMany(Flux.just(new UserResponse(
                                            customer.getId().getValue(),
                                            userDTO.getName(),
                                            userDTO.getDocumentId()
                                    )));
                        })
                );
    }
}