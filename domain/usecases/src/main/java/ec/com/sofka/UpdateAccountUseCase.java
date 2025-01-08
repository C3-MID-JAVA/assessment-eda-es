package ec.com.sofka;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.request.account.UpdateAccountRequest;
import ec.com.sofka.responses.account.UpdateAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateAccountUseCase implements IUseCase<UpdateAccountRequest, Mono<UpdateAccountResponse>> {
    private final AccountRepository accountRepository;
    private final IEventStore eventRepository;

    public UpdateAccountUseCase(AccountRepository accountRepository, IEventStore eventRepository) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
    }
    @Override
    public Mono<UpdateAccountResponse> execute(UpdateAccountRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    Customer customer = Customer.from(request.getAggregateId(), events);

                    customer.updateAccount(
                            customer.getAccount().getId().getValue(),
                            request.getBalance(),
                            request.getNumber(),
                            request.getCustomerName(),
                            request.getStatus(),
                            request.getIdUser());

                    AccountDTO accountDTO = new AccountDTO(
                            customer.getAccount().getId().getValue(),
                            customer.getAccount().getBalance().getValue(),
                            customer.getAccount().getName().getValue(),
                            customer.getAccount().getNumber().getValue(),
                            customer.getAccount().getUserId().getValue(),
                            customer.getAccount().getStatus().getValue()
                    );

                    return accountRepository.update(accountDTO)
                            .flatMap(updatedAccount -> {
                                if (updatedAccount == null) {
                                    return Mono.just(new UpdateAccountResponse());
                                }
                                return Mono.when(
                                                Mono.just(updatedAccount)
                                                        .doOnError(e -> {
                                                            System.err.println("Error processing updated account: " + e.getMessage());
                                                        }),
                                                Flux.fromIterable(customer.getUncommittedEvents())
                                                        .flatMap(eventRepository::save)
                                                        .doOnError(e -> {
                                                            System.err.println("Error saving events: " + e.getMessage());
                                                        })
                                                        .then()
                                        )
                                        .doOnError(e -> {
                                            System.err.println("Error in combined flow: " + e.getMessage());
                                        })
                                        .then(Mono.fromRunnable(customer::markEventsAsCommitted))
                                        .doOnError(e -> {
                                            System.err.println("Error marking events as committed: " + e.getMessage());
                                        })
                                        .thenReturn(new UpdateAccountResponse(
                                                request.getAggregateId(),
                                                updatedAccount.getId(),
                                                updatedAccount.getAccountNumber(),
                                                updatedAccount.getOwner(),
                                                updatedAccount.getBalance(),
                                                updatedAccount.getStatus()
                                        ))
                                        .doOnError(e -> {
                                            System.err.println("Error creating response: " + e.getMessage());
                                        });
                            })
                            .onErrorResume(e -> {
                                System.err.println("General error: " + e.getMessage());
                                return Mono.error(e);
                            });
                });
    }
}
