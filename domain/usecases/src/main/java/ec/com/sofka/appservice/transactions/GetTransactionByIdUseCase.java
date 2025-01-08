package ec.com.sofka.appservice.transactions;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetTransactionByIdUseCase implements IUseCase<GetByElementRequest, TransactionResponse> {

    private final ITransactionRepository repository;
    private final IEventStore eventRepository;
    public GetTransactionByIdUseCase(ITransactionRepository repository, IEventStore eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }


    @Override
    public Mono<TransactionResponse> execute(GetByElementRequest request) {
        // Obtener los eventos asociados al customer a partir de su ID
        return eventRepository.findAggregate(request.getAggregateId()) // Encuentra el agregado (Customer)
                .collectList() // Convertimos el Flux<DomainEvent> en Mono<List<DomainEvent>>
                .map(eventsList -> Customer.from(request.getAggregateId(), eventsList)) // Reconstruir el agregado Customer
                .flatMap(customer -> {
                    // Buscar la transacción en la lista de transacciones del customer utilizando el TransactionId
                    return Mono.justOrEmpty(
                                    customer.getTransactions().stream()
                                            .filter(transaction -> transaction.getId().getValue().equals(request.getElement())) // Buscar por TransactionId
                                            .findFirst() // Encontrar la primera coincidencia
                            )
                            .switchIfEmpty(Mono.error(new NoSuchElementException("Transaction not found with id: " + request.getElement()))) // Si no se encuentra la transacción, lanzar error
                            .map(transaction -> {
                                // Si encontramos la transacción, construir la respuesta

                                return new TransactionResponse(
                                        request.getAggregateId(),
                                        transaction.getId().getValue(),  // TransactionId
                                        transaction.getAccountId().getValue(), // Usar accountId o accountNumber dependiendo de la situación
                                        transaction.getTransactionCost().getValue(),
                                        transaction.getAmount().getValue(),
                                        transaction.getDate().getValue(),
                                        transaction.getType() // Asumimos que el tipo es un OperationType enum
                                );
                            });
                });
    }

}
