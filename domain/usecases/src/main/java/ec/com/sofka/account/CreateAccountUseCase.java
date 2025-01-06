package ec.com.sofka.account;


import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.account.request.CreateAccountRequest;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.account.responses.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

//Usage of the IUseCase interface
public class CreateAccountUseCase implements IUseCase<CreateAccountRequest, AccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }


    //Of course, you have to create that class Response in usecases module on a package called responses or you can also group the command with their response class in a folder (Screaming architecture)
    //You maybe want to check Jacobo's repository to see how he did it
    public Mono<AccountResponse> execute(CreateAccountRequest cmd) {
        //Create the aggregate, remember this usecase is to create the account the first time so just have to create it.
        Customer customer = new Customer();

        //Then we create the account
        customer.createAccount(cmd.getBalance(), cmd.getAccountNumber(), cmd.getUserId());

        //Save the account on the account repository
        return accountRepository.save(new AccountDTO(
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getAccountNumber().getValue(),
                        customer.getAccount().getUserId().getValue(),
                        customer.getId().getValue()
                ))
                .flatMap(savedAccount -> {
                    return Flux.fromIterable(customer.getUncommittedEvents())
                            .flatMap(repository::save)
                            .then(Mono.just(savedAccount));
                })
                .doOnTerminate(customer::markEventsAsCommitted)
                .thenReturn(new AccountResponse(
                        customer.getId().getValue(),
                        customer.getAccount().getAccountNumber().getValue(),
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getUserId().getValue()
                ));
    }
}
