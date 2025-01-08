package ec.com.sofka;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.request.account.GetAccountRequest;
import ec.com.sofka.responses.account.GetAccountResponse;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase implements IUseCase<GetAccountRequest, Mono<GetAccountResponse>> {

    private final AccountRepository accountRepository;
    private final IEventStore eventStore;

    public GetAccountByNumberUseCase(AccountRepository accountRepository, IEventStore eventStore) {
        this.accountRepository = accountRepository;
        this.eventStore = eventStore;
    }

    @Override
    public Mono<GetAccountResponse> execute(GetAccountRequest request) {
        return eventStore.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    Customer customer = Customer.from(request.getAggregateId(), events);
                    return accountRepository.findByNumber(customer.getAccount().getNumber().getValue())
                            .doOnNext(result -> {
                                System.out.println("Result: " + result.getId() + "  "+
                                        result.getAccountNumber()+ "  "+
                                        result.getOwner()+ "  "+
                                        result.getBalance()+ "  "+
                                        result.getStatus());
                            })
                            .map(result -> new GetAccountResponse(
                                    request.getAggregateId(),
                                    result.getId(),
                                    result.getAccountNumber(),
                                    result.getOwner(),
                                    result.getBalance(),
                                    result.getStatus()
                            ));
                });
    }
}