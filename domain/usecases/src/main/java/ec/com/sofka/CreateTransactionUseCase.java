package ec.com.sofka;

import ec.com.sofka.account.Account;
import ec.com.sofka.aggregate.Operation;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.request.transaction.CreateTransactionRequest;
import ec.com.sofka.responses.account.CreateAccountResponse;
import ec.com.sofka.responses.transaction.CreateTransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CreateTransactionUseCase implements IUseCase<CreateTransactionRequest, Mono<CreateTransactionResponse>> {
    private final IEventStore eventStore;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public CreateTransactionUseCase(IEventStore eventStore, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.eventStore = eventStore;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Mono<CreateTransactionResponse> execute(CreateTransactionRequest request) {
        return null;
    }

    public Mono<CreateTransactionResponse> validarTransaction2(AccountDTO cuenta, BigDecimal monto,  String tipo, BigDecimal costo, boolean  esRetiro){
        if (esRetiro && cuenta.getBalance().compareTo(monto.add(costo)) < 0) {
            throw new RuntimeException("Saldo insuficiente para realizar esta transacción");
        }

        actualizarSaldo(cuenta, monto, costo, tipo, esRetiro);

        Operation operation = new Operation();
        operation.createTransaction(monto, tipo,costo,cuenta.getId(),cuenta.getStatus());

        TransactionDTO transactionDTO = new TransactionDTO(
                operation.getTransaction().getId().getValue(),
                operation.getTransaction().getAmount().getValue(),
                operation.getTransaction().getType().getValue(),
                operation.getTransaction().getCost().getValue(),
                operation.getTransaction().getIdAccount().getValue(),
                operation.getTransaction().getStatus().getValue()
        );

        AccountDTO cuentaActualizada= new AccountDTO(
                cuenta.getId(),
                cuenta.getBalance(),
                cuenta.getOwner(),
                cuenta.getAccountNumber(),
                cuenta.getStatus()
        );
        return accountRepository.update(cuentaActualizada)
                .flatMap(savedAcounted-> transactionRepository.save(transactionDTO)
                        .flatMap(transactionSaved->{
                            return Mono.when(
                                    Mono.just(transactionSaved),
                                    Flux.fromIterable(operation.getUncommittedEvents())
                                            .flatMap(eventStore::save)
                                            .then()
                            )
                                    .then(Mono.fromRunnable(operation::markEventsAsCommitted))
                                    .thenReturn(new CreateTransactionResponse(
                                            operation.getId().getValue(),
                                            operation.getTransaction().getId().getValue(),
                                            operation.getTransaction().getAmount().getValue(),
                                            operation.getTransaction().getType().getValue(),
                                            operation.getTransaction().getCost().getValue(),
                                            operation.getTransaction().getIdAccount().getValue(),
                                            operation.getTransaction().getStatus().getValue()
                                    ));
                        }));

                //Construir el objeto  UpdateAccountRequest para envair actualizar a BD normal y de eventos.
        /// Mando a llamar al metodo actualizar de account y se envia el objeto AccountDTO

    }


    private void actualizarSaldo(AccountDTO cuenta, BigDecimal monto, BigDecimal costo, String tipo, boolean esRetiro) {
        if (esRetiro) {
            cuenta.setBalance(cuenta.getBalance().subtract(monto.add(costo)));
        } else {
            cuenta.setBalance(cuenta.getBalance().add(monto).subtract(costo));
        }
    }

/*
    @Override
    public Mono<AccountResponse> get(GetElementRequest request) {

        return getUserByAggregate(request.getAggregateId())
                .map(AccountMapper::mapToResponseFromModel);
    }




    public Mono<Account> getUserByAggregate(String aggregateId) {
        return eventStore.findAggregate(aggregateId)
                .switchIfEmpty(Mono.defer(() -> {
                    return Mono.error(new RuntimeException("Account not found."));
                }))
                .collectList()
                .map(eventsList -> AccountAggregate.from(aggregateId, eventsList))
                .flatMap(accountAggregate ->
                        accountRepository.findByNumber(accountAggregate.getAccount().getId().getValue())
                                .map(AccountMapper::mapToModelFromDTO)
                );
    }  */
}
