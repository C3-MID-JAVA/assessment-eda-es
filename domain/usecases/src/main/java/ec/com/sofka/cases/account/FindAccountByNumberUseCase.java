package ec.com.sofka.cases.account;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.exception.RecordNotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.ErrorBusMessage;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.interfaces.IUseCaseGetElement;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.model.ErrorMessage;
import ec.com.sofka.requests.GetElementRequest;
import ec.com.sofka.responses.AccountResponse;
import reactor.core.publisher.Mono;

public class FindAccountByNumberUseCase implements IUseCaseGetElement<GetElementRequest, AccountResponse> {
    private final AccountRepository accountRepository;
    private final IEventStore eventStore;
    private final ErrorBusMessage errorBusMessage;

    public FindAccountByNumberUseCase(AccountRepository accountRepository, IEventStore eventStore, ErrorBusMessage errorBusMessage) {
        this.accountRepository = accountRepository;
        this.eventStore = eventStore;
        this.errorBusMessage = errorBusMessage;
    }

    @Override
    public Mono<AccountResponse> get(GetElementRequest request) {

        return getByAggregate(request.getAggregateId())
                .map(AccountMapper::mapToResponseFromModel);
    }

    public Mono<Account> getByAggregate(String aggregateId) {
        return eventStore.findAggregate(aggregateId)
                .switchIfEmpty(Mono.defer(() -> {
                    errorBusMessage.sendMsg(new ErrorMessage("Account not found", "Get Account by NumberAccount"));
                    return Mono.error(new RecordNotFoundException("Account not found."));
                }))
                .collectList()
                .map(eventsList -> AccountAggregate.from(aggregateId, eventsList))
                .flatMap(accountAggregate ->
                        accountRepository.findByNumber(accountAggregate.getAccount().getAccountNumber().getValue())
                                .map(AccountMapper::mapToModelFromDTO)
                );
    }

}