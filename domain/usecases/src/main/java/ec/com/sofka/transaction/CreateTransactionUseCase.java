package ec.com.sofka.transaction;

import ec.com.sofka.TransactionStrategy;
import ec.com.sofka.TransactionStrategyFactory;
import ec.com.sofka.aggregate.customer.Customer;
import ec.com.sofka.aggregate.operation.Operation;
import ec.com.sofka.exception.BadRequestException;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.transaction.request.CreateTransactionRequest;
import ec.com.sofka.transaction.responses.TransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateTransactionUseCase implements IUseCase<CreateTransactionRequest, TransactionResponse> {
    private final IEventStore repository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionStrategyFactory strategyFactory;

    public CreateTransactionUseCase(IEventStore repository, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionStrategyFactory strategyFactory) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.strategyFactory = strategyFactory;
    }

    @Override
    public Mono<TransactionResponse> execute(CreateTransactionRequest cmd) {
        return accountRepository.findByAccountNumber(cmd.getAccountId())
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
                .flatMap(accountDTO -> {
                    Operation operation = new Operation();

                    TransactionStrategy strategy = strategyFactory.getStrategy(cmd.getType());
                    BigDecimal fee = strategy.calculateFee();
                    BigDecimal netAmount = cmd.getAmount().add(fee);
                    BigDecimal balance = strategy.calculateBalance(accountDTO.getBalance(), cmd.getAmount());

                    if (balance.compareTo(BigDecimal.ZERO) < 0) {
                        throw new BadRequestException("Insufficient balance for this transaction.");
                    }

                    operation.createTransaction(
                            cmd.getAmount(),
                            fee,
                            netAmount,
                            LocalDateTime.now(),
                            cmd.getType(),
                            accountDTO.getId()
                    );

                    return transactionRepository.save(new TransactionDTO(
                                    operation.getTransaction().getAmount().getValue(),
                                    operation.getTransaction().getFee().getValue(),
                                    operation.getTransaction().getNetAmount().getValue(),
                                    operation.getTransaction().getType().getValue(),
                                    operation.getTransaction().getTimestamp().getValue(),
                                    operation.getTransaction().getAccountId().getValue(),
                                    operation.getId().getValue()
                            ))
                            .flatMap(transactionDTO -> {
                                accountDTO.setBalance(balance);
                                return accountRepository.save(accountDTO);
                            })
                            .flatMap(savedAccount -> {
                                return repository.findAggregate(savedAccount.getCustomerId())
                                        .collectList()
                                        .flatMap(events -> Customer.from(savedAccount.getCustomerId(), Flux.fromIterable(events)))
                                        .flatMap(customer -> {
                                            customer.updateAccountBalance(balance, savedAccount.getAccountNumber(), savedAccount.getUserId());

                                            return Flux.concat(
                                                            Flux.fromIterable(operation.getUncommittedEvents())
                                                                    .flatMap(repository::save),
                                                            Flux.fromIterable(customer.getUncommittedEvents())
                                                                    .flatMap(repository::save)
                                                    )
                                                    .doOnTerminate(() -> {
                                                        operation.markEventsAsCommitted();
                                                        customer.markEventsAsCommitted();
                                                    })
                                                    .then()
                                                    .thenReturn(new TransactionResponse(
                                                            operation.getId().getValue(),
                                                            operation.getTransaction().getAmount().getValue(),
                                                            operation.getTransaction().getFee().getValue(),
                                                            operation.getTransaction().getNetAmount().getValue(),
                                                            operation.getTransaction().getType().getValue(),
                                                            operation.getTransaction().getTimestamp().getValue(),
                                                            operation.getTransaction().getAccountId().getValue()
                                                    ));
                                        });
                            });

                });
    }
}
