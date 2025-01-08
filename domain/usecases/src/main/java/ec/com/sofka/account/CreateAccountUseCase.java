package ec.com.sofka.account;


import ec.com.sofka.aggregate.account.Customer;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.request.CreateAccountRequest;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.responses.CreateAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class CreateAccountUseCase implements IUseCase<CreateAccountRequest, CreateAccountResponse> {
    private final IEventStore eventRepository;
    private final IAccountRepository accountRepository;

    public CreateAccountUseCase(IEventStore repository, IAccountRepository accountRepository) {
        this.eventRepository = repository;
        this.accountRepository = accountRepository;
    }


    @Override
    public Mono<CreateAccountResponse> execute(CreateAccountRequest cmd) {

        return accountRepository.findByAccountNumber(cmd.getAccountNumber())
                .flatMap(existingAccount-> Mono.error(new RuntimeException("Account already exist")))
                .switchIfEmpty(Mono.defer(() ->{
                    Customer customer = new Customer();

                    customer.createAccount(cmd.getBalance(), cmd.getAccountNumber(), cmd.getOwnerName(), cmd.getAccountType());

                    return accountRepository
                            .save(new AccountDTO(
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getOwnerName().getValue(),
                                    customer.getAccount().getAccountNumber().getValue(),
                                    customer.getAccount().getType().getValue()
                            ))
                            .doOnSuccess(account -> System.out.println("ACCOUNT CREATED: " + account))
                            .flatMap(savedAccount -> Flux.fromIterable(customer.getUncommittedEvents())
                                    .flatMap(eventRepository::save)
                                    .then(Mono.just(savedAccount))
                            )
                            .doOnSuccess(savedAccount -> customer.markEventsAsCommitted())
                            .map(savedAccount -> new CreateAccountResponse(
                                    customer.getId().getValue(),
                                    customer.getAccount().getAccountNumber().getValue(),
                                    customer.getAccount().getOwnerName().getValue(),
                                    customer.getAccount().getType().getValue(),
                                    customer.getAccount().getBalance().getValue()
                            ));
                }))
                .cast(CreateAccountResponse.class);

    }

}
