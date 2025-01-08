package com.bank.appservice.usecases.account;

import com.bank.aggregate.Customer;
import com.bank.appservice.request.CreateAccountRequest;
import com.bank.appservice.response.CreateAccountResponse;
import com.bank.gateway.IAccountRepository;
import com.bank.gateway.IBusMessage;
import com.bank.gateway.IEventStore;
import com.bank.gateway.dto.AccountDTO;
import com.bank.generics.interfaces.IUseCaseExecute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase implements IUseCaseExecute<CreateAccountRequest, CreateAccountResponse> {
    private final IAccountRepository repository;
    private final IBusMessage busMessage;
    private final IEventStore eventRepository;

    public CreateAccountUseCase(IAccountRepository repository, IBusMessage busMessage, IEventStore eventRepository) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<CreateAccountResponse> execute(CreateAccountRequest request) {
        Customer customer = new Customer();

        customer.createAccount(request.getAccountNumber(), request.getAccountHolder(), request.getAccountBalance(), request.getAccountIsActive());

        return repository.createAccount(
                new AccountDTO(
                        customer.getAccount().getId().getValue(),
                        customer.getAccount().getNumber().getValue(),
                        customer.getAccount().getHolder().getValue(),
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getIsActive().getValue()

                ))
                        .flatMap(savedAcc -> {
                            return Flux.fromIterable(customer.getUncommittedEvents())
                                    .flatMap(eventRepository::save)
                                    .then(Mono.just(savedAcc));
                        })
                .doOnTerminate(customer::markEventsAsCommitted)
                .thenReturn(new CreateAccountResponse(
                        customer.getId().getValue(),
                        customer.getAccount().getId().getValue(),
                        customer.getAccount().getNumber().getValue(),
                        customer.getAccount().getHolder().getValue(),
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getIsActive().getValue()
                ));


        //busMessage.sendMsg("Creating account " + request.getAccountNumber() + " for holder " + request.getAccountHolder() + ".");
    }
}

/*
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
 */