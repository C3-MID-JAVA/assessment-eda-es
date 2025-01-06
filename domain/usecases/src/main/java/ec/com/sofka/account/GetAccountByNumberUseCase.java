package ec.com.sofka.account;

import ec.com.sofka.account.request.GetAccountByNumberRequest;
import ec.com.sofka.account.responses.AccountResponse;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase implements IUseCase<GetAccountByNumberRequest, AccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public GetAccountByNumberUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<AccountResponse> execute(GetAccountByNumberRequest cmd) {
        return accountRepository.findByAccountNumber(cmd.getAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
                .flatMap(accountDTO ->
                        repository.findAggregate(accountDTO.getCustomerId())
                                .collectList() // Recopila los eventos como una lista
                                .flatMap(events -> {
                                    // Construye el agregado usando la lista de eventos
                                    Customer customer = Customer.from(accountDTO.getId(), events);

                                    // Aplica lógica de negocio al agregado
                                    customer.retrieveAccount(
                                            accountDTO.getBalance(),
                                            accountDTO.getAccountNumber(),
                                            accountDTO.getUserId()
                                    );

                                    // Procesa y guarda los eventos no comprometidos
                                    return Flux.fromIterable(customer.getUncommittedEvents())
                                            .flatMap(repository::save)
                                            .then(Mono.just(new AccountResponse(
                                                    customer.getId().getValue(),
                                                    accountDTO.getAccountNumber(),
                                                    accountDTO.getBalance(),
                                                    accountDTO.getUserId()
                                            )));
                                })
                );
    }

}
