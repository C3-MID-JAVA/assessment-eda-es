package ec.com.sofka.cases.transaction;

import ec.com.sofka.aggregate.AccountAggregate;
import ec.com.sofka.cases.account.UpdateAccountUseCase;
import ec.com.sofka.cases.transcationType.FindTransactionTypeByIdUseCase;
import ec.com.sofka.gateway.*;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.mapper.TransactionTypeMapper;
import ec.com.sofka.model.TransactionMessage;
import ec.com.sofka.aggregate.entities.transaction.Transaction;
import ec.com.sofka.aggregate.entities.transaction.values.objects.ProcessingDate;
import ec.com.sofka.requests.AccountRequest;
import ec.com.sofka.requests.TransactionRequest;
import ec.com.sofka.responses.TransactionResponse;
import ec.com.sofka.rules.BalanceCalculator;
import ec.com.sofka.rules.ValidateTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CreateTransactionUseCase implements IUseCaseExecute<TransactionRequest, TransactionResponse> {
    private final IEventStore repository;
    private final TransactionRepository transactionRepository;
    private final ValidateTransaction validateTransaction;
    private final BalanceCalculator balanceCalculator;
    private final FindTransactionTypeByIdUseCase findTransactionTypeByIdUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final TransactionBusMessage transactionBusMessage;

    public CreateTransactionUseCase(IEventStore repository, TransactionRepository transactionRepository, ValidateTransaction validateTransaction, BalanceCalculator balanceCalculator, FindTransactionTypeByIdUseCase findTransactionTypeByIdUseCase, TransactionBusMessage transactionBusMessage, UpdateAccountUseCase updateAccountUseCase) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.updateAccountUseCase = updateAccountUseCase;
        this.validateTransaction = validateTransaction;
        this.balanceCalculator = balanceCalculator;
        this.findTransactionTypeByIdUseCase = findTransactionTypeByIdUseCase;
        this.transactionBusMessage = transactionBusMessage;
    }

    @Override
    public Mono<TransactionResponse> execute(TransactionRequest transactionRequest) {
        return findTransactionTypeByIdUseCase.getUserByAggregate(transactionRequest.getTransactionTypeAggregateId())
                .map(TransactionTypeMapper::mapToDTOFromModel)
                .map(transactionType ->
                        new TransactionDTO(
                                transactionRequest.getAccountNumber(),
                                transactionRequest.getDetails(),
                                transactionRequest.getAmount(),
                                null,
                                null,
                                transactionType))
                .flatMap(transactionDTO -> validateTransaction.validateTransaction(transactionDTO, transactionRequest.getAccountAggregateId()))
                .flatMap(transactionDTO -> updateBalanceAndSave(transactionDTO, transactionRequest.getAccountAggregateId()));

    }

    public Mono<TransactionResponse> updateBalanceAndSave(TransactionDTO transaction, String accountAggregateId) {
        AccountAggregate accountAggregate = new AccountAggregate();

        BigDecimal newBalance = balanceCalculator.calculate(transaction, transaction.getAccount().getBalance());
        transaction.getAccount().setBalance(newBalance);
        transaction.setProcessingDate(new ProcessingDate(transaction.getProcessingDate()).getValue());

        accountAggregate.createTransaction(
                transaction.getAccountNumber(),
                transaction.getDetails(),
                transaction.getAmount(),
                transaction.getProcessingDate(),
                AccountMapper.mapToModelFromDTO(transaction.getAccount()),
                TransactionTypeMapper.mapToModelFromDTO(transaction.getTransactionType())
        );

        AccountRequest accountRequest = new AccountRequest(
                accountAggregateId,
                transaction.getAccount().getAccountNumber(),
                transaction.getAccount().getBalance(),
                transaction.getAccount().getStatus(),
                transaction.getAccount().getUser().getId(),
                null
        );

        return updateAccountUseCase.execute(accountRequest)
                .then(transactionRepository.save(transaction))
                .flatMap(transactionDTO -> {
                    transactionBusMessage.sendMsg(
                            new TransactionMessage(transactionDTO.getId(),
                                    transactionDTO.getAccountNumber(),
                                    transactionDTO.getTransactionType().getType(),
                                    transactionDTO.getAmount()));

                    return Flux.fromIterable(accountAggregate.getUncommittedEvents())
                        .flatMap(repository::save)
                        .then(Mono.just(transactionDTO));
                })
                .mapNotNull(transactionDTO -> {
                    accountAggregate.markEventsAsCommitted();
                    return TransactionMapper.mapToResponseFromDTO(transaction);
                });
    }

}