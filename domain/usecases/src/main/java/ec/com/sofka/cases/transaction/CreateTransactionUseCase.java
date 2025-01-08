package ec.com.sofka.cases.transaction;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.ITransactionRepository;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.requests.TransactionRequest;
import ec.com.sofka.responses.TransactionResponse;
import ec.com.sofka.rules.IValidateTransaction;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CreateTransactionUseCase implements IUseCaseExecute<TransactionRequest, Mono<TransactionResponse>> {
    private final IEventStore repository;
    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;
    private final IValidateTransaction validateTransaction;

    public CreateTransactionUseCase(
            IEventStore repository,
            ITransactionRepository transactionRepository,
            IAccountRepository accountRepository,
            IValidateTransaction validateTransaction
    ) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.validateTransaction = validateTransaction;
    }

    @Override
    public Mono<TransactionResponse> execute(TransactionRequest transactionRequest) {
        return accountRepository.findByAccountId(transactionRequest.getAccountId())
                .flatMap(account -> {
                    TransactionDTO transactionDTO = new TransactionDTO(
                            null,
                            account.getAccountNumber(),
                            null,
                            transactionRequest.getAmount(),
                            transactionRequest.getProcessingDate(),
                            account,
                            transactionRequest.getTransactionType(),
                            new BigDecimal(transactionRequest.getTransactionCost())
                    );

                    return validateTransaction.validateTransaction(transactionDTO, transactionRequest.getAggregateId())
                            .flatMap(this::updateBalanceAndSave);
                })
                .map(TransactionMapper::mapToResponseFromModel);
    }

    public Mono<Transaction> updateBalanceAndSave(TransactionDTO transactionDTO) {
        return accountRepository.findByAccountId(transactionDTO.getAccount().getId())
                .flatMap(account -> {
                    BigDecimal newBalance = account.getBalance().add(transactionDTO.getAmount());
                    account.setBalance(newBalance);

                    Customer customer = Customer.from(
                            transactionDTO.getAccount().getId(),
                            repository.findAggregate(transactionDTO.getAccount().getId()).collectList().block()
                    );

                    customer.createTransaction(
                            transactionDTO.getAccount().getAccountNumber(),
                            transactionDTO.getAmount(),
                            transactionDTO.getProcessingDate(),
                            AccountMapper.mapToModelFromDTO(transactionDTO.getAccount()),
                            transactionDTO.getTransactionType(),
                            transactionDTO.getTransactionCost()
                    );

                    return accountRepository.save(transactionDTO.getAccount())
                            .then(transactionRepository.create(TransactionMapper.mapToModelFromDTO(transactionDTO)))
                            .flatMap(savedTransaction -> {
                                repository.saveAll(customer.getUncommittedEvents()).subscribe();
                                customer.markEventsAsCommitted();
                                return Mono.just(savedTransaction);
                            });
                });
    }
}
