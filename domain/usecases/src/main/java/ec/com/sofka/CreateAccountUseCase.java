package ec.com.sofka;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.request.account.CreateAccountRequest;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.responses.account.CreateAccountResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public class CreateAccountUseCase implements IUseCase<CreateAccountRequest, Mono<CreateAccountResponse>> {
    private final IEventStore eventStore;
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(IEventStore eventStore, AccountRepository accountRepository) {
        this.eventStore = eventStore;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<CreateAccountResponse> execute(CreateAccountRequest cmd) {
        Customer customer = new Customer();
        customer.createAccount(cmd.getBalance(), cmd.getNumber(), cmd.getCustomerName(), cmd.getIdUser(), cmd.getStatus());

        AccountDTO accountDTO = new AccountDTO(
                customer.getAccount().getId().getValue(),
                customer.getAccount().getBalance().getValue(),
                customer.getAccount().getName().getValue(),
                customer.getAccount().getNumber().getValue(),
                customer.getAccount().getUserId().getValue(),
                customer.getAccount().getStatus().getValue()
        );
        return accountRepository.save(accountDTO)
                .flatMap(savedAccount -> {
                    return Mono.when(
                                    Mono.just(savedAccount),
                                    Flux.fromIterable(customer.getUncommittedEvents())
                                            .flatMap(eventStore::save)
                                            .then()
                            )
                            .then(Mono.fromRunnable(customer::markEventsAsCommitted))
                            .thenReturn(new CreateAccountResponse(
                                    customer.getId().getValue(),
                                    customer.getAccount().getId().getValue(),
                                    customer.getAccount().getNumber().getValue(),
                                    customer.getAccount().getName().getValue(),
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getStatus().getValue()
                            ));
                })
                .onErrorResume(e -> {
                    // Manejo de error general
                    System.err.println("Error general: " + e.getMessage());
                    return Mono.error(e);
                });
/*
        return accountRepository.save(accountDTO)
                .doOnError(e -> {
                    // Manejo de error al guardar la cuenta
                    System.err.println("Error al guardar la cuenta: " + e.getMessage());
                })
                .flatMap(savedAccount -> {
                    return Mono.when(
                                    Mono.just(savedAccount)
                                            .doOnError(e -> {
                                                // Manejo de error al procesar la cuenta guardada
                                                System.err.println("Error al procesar la cuenta guardada: " + e.getMessage());
                                            }),
                                    Flux.fromIterable(customer.getUncommittedEvents())
                                            .flatMap(eventStore::save)
                                            .doOnError(e -> {
                                                // Manejo de error al guardar eventos
                                                System.err.println("Error al guardar eventos: " + e.getMessage());
                                            })
                                            .then()
                            )
                            .doOnError(e -> {
                                // Manejo de error en el flujo combinado
                                System.err.println("Error en el flujo combinado: " + e.getMessage());
                            })
                            .then(Mono.fromRunnable(customer::markEventsAsCommitted))
                            .doOnError(e -> {
                                // Manejo de error al marcar eventos como comprometidos
                                System.err.println("Error al marcar eventos como comprometidos: " + e.getMessage());
                            })
                            .thenReturn(new CreateAccountResponse(
                                    customer.getId().getValue(),
                                    customer.getAccount().getId().getValue(),
                                    customer.getAccount().getNumber().getValue(),
                                    customer.getAccount().getName().getValue(),
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getStatus().getValue()
                            ))
                            .doOnError(e -> {
                                // Manejo de error al crear la respuesta
                                System.err.println("Error al crear la respuesta: " + e.getMessage());
                            });
                })
                .onErrorResume(e -> {
                    // Manejo de error general
                    System.err.println("Error general: " + e.getMessage());
                    return Mono.error(e);
                });*/
    }
}