package ec.com.sofka.account;



import ec.com.sofka.aggregate.account.Customer;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.request.GetAccountRequest;
import ec.com.sofka.responses.CreateAccountResponse;
import ec.com.sofka.responses.GetAccountByNumResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class GetAccountByAccountNumberUseCase implements IUseCase<GetAccountRequest, GetAccountByNumResponse> {
    private final IAccountRepository accountRepository;
    private final IEventStore eventStore;

    public GetAccountByAccountNumberUseCase(IAccountRepository accountRepository, IEventStore eventStore) {
        this.accountRepository = accountRepository;
        this.eventStore = eventStore;
    }

    @Override
    public Mono<GetAccountByNumResponse> execute(GetAccountRequest request) {
        Flux<DomainEvent> events = eventStore.findAggregate(request.getAggregateId());

        return Customer.from(request.getAggregateId(), events)
                .flatMap(customer -> {
                    Account account = customer.getAccount();
                    if (account == null) {
                        return Mono.error(new RuntimeException("Account not found in event store"));
                    }
                    return accountRepository.findByAccountNumber(account.getAccountNumber().getValue())
                            .switchIfEmpty(Mono.error(new RuntimeException("Account not found in repository")))
                            .map(accountDTO -> new GetAccountByNumResponse(accountDTO, account));
                });
    }
}
