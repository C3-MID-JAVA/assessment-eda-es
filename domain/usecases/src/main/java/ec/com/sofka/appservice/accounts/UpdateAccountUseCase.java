package ec.com.sofka.appservice.accounts;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.data.request.UpdateAccountRequest;
import ec.com.sofka.appservice.data.response.UpdateAccountResponse;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.appservice.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateAccountUseCase implements IUseCase<UpdateAccountRequest, UpdateAccountResponse> {

    private final IAccountRepository accountRepository;
    private final IEventStore eventRepository;

    public UpdateAccountUseCase(IAccountRepository accountRepository, IEventStore eventRepository) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
    }


    @Override
    public Mono<UpdateAccountResponse> execute(UpdateAccountRequest request) {
        return eventRepository.findAggregate(request.getAggregateId()) // Obtener eventos como Flux<DomainEvent>
                .collectList() // Agrupar los eventos en una lista
                .flatMap(events -> {
                    // Reconstruir el aggregate usando el método from
                    Customer customer = Customer.from(request.getAggregateId(), events);

                    // Actualizar datos en el cliente
                    customer.updateAccount(
                            customer.getAccount().getId().getValue(),
                            request.getBalance(),
                            request.getNumber(),
                            request.getCustomerName(),
                            request.getStatus()
                    );

                    // Crear DTO para la actualización de la cuenta
                    AccountDTO accountDTO = new AccountDTO(
                            customer.getAccount().getId().getValue(),
                            request.getCustomerName(),
                            request.getNumber(),
                            customer.getAccount().getBalance().getValue(),
                            customer.getAccount().getStatus().getValue()
                    );

                    // Actualizar la cuenta en el repositorio de manera reactiva
                    return accountRepository.update(accountDTO)
                            .flatMap(result -> {
                                // Guardar los eventos no comprometidos de forma reactiva
                                return Flux.fromIterable(customer.getUncommittedEvents())
                                        .flatMap(eventRepository::save)
                                        .then(Mono.defer(() -> {
                                            // Marcar eventos como comprometidos
                                            customer.markEventsAsCommitted();

                                            // Crear respuesta después de la actualización
                                            return Mono.just(new UpdateAccountResponse(
                                                    request.getAggregateId(),
                                                    result.getId(),
                                                    result.getAccountNumber(),
                                                    result.getName(),
                                                    result.getStatus()
                                            ));
                                        }));
                            });
                })
                .defaultIfEmpty(new UpdateAccountResponse()); // Manejar el caso donde no hay eventos
    }



}
