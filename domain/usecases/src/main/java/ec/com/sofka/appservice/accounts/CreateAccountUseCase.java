package ec.com.sofka.appservice.accounts;

import ec.com.sofka.account.Account;
import ec.com.sofka.ConflictException;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.appservice.accounts.request.CreateAccountRequest;
import ec.com.sofka.appservice.accounts.response.CreateAccountResponse;
import ec.com.sofka.appservice.gateway.IBusMessage;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.IEventStore;
import ec.com.sofka.appservice.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase implements IUseCase <CreateAccountRequest,CreateAccountResponse>{

    private final IAccountRepository accountRepository;
    private final IEventStore repository;
    private final IBusMessage busMessage;
    public CreateAccountUseCase(IAccountRepository accountRepository, IEventStore repository, IBusMessage busMessage) {
        this.accountRepository = accountRepository;
        this.repository = repository;
        this.busMessage = busMessage;
    }

    @Override
    public Mono<CreateAccountResponse> execute(CreateAccountRequest cmd) {
        // Verificar si ya existe una cuenta con el mismo número
        return accountRepository.findByAccountNumber(cmd.getNumber())
                .flatMap(existingAccount -> Mono.<CreateAccountResponse>error(new ConflictException("The account number is already registered.")))
                .switchIfEmpty(Mono.defer(() -> {
                    // Crear un cliente y una cuenta
                    Customer customer = new Customer();
                    customer.createAccount(cmd.getNumber(), cmd.getBalance(), cmd.getCustomerName(), cmd.getStatus());

                    // Guardar la cuenta en el repositorio
                    return accountRepository.save(
                                    new AccountDTO(
                                            customer.getAccount().getId().getValue(),
                                            customer.getAccount().getOwner().getValue(),
                                            customer.getAccount().getAccountNumber().getValue(),
                                            customer.getAccount().getBalance().getValue(),
                                            customer.getAccount().getStatus().getValue()
                                    )
                            ).onErrorResume(e -> {
                                // Maneja el error, por ejemplo, registrando el error
                                return Mono.error(new RuntimeException("Error al guardar la cuenta"));
                            })
                            .doOnSuccess(savedAccount -> {
                                // Enviar mensaje de evento después de guardar
                                busMessage.sendMsg("account", "Create account: " + savedAccount.toString());
                            })
                            .flatMap(savedAccount -> Flux.fromIterable(customer.getUncommittedEvents())
                                    .flatMap(repository::save)
                                    .then(
                                            Mono.just(new CreateAccountResponse(
                                                            customer.getId().getValue(),
                                                            customer.getAccount().getId().getValue(),
                                                            customer.getAccount().getAccountNumber().getValue(),
                                                            customer.getAccount().getOwner().getValue(),
                                                            customer.getAccount().getBalance().getValue(),
                                                            customer.getAccount().getStatus().getValue())
                                    ))
                            )
                            .doOnSuccess(saved -> {
                                customer.markEventsAsCommitted();
                            });

                }));
    }

}
