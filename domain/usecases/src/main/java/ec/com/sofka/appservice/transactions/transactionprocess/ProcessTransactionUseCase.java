package ec.com.sofka.appservice.transactions.transactionprocess;

import ec.com.sofka.ConflictException;
import ec.com.sofka.appservice.data.request.CreateTransactionRequest;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.accounts.GetAccountByAccountNumberUseCase;
import ec.com.sofka.appservice.accounts.SaveAccountUseCase;
import ec.com.sofka.enums.OperationType;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class ProcessTransactionUseCase {

    private final GetAccountByAccountNumberUseCase getAccountByNumberUseCase;
    private final GetTransactionStrategyUseCase getTransactionStrategyUseCase;
    private final CalculateFinalBalanceUseCase calculateFinalBalanceUseCase;
    private final SaveAccountUseCase saveAccountUseCase;
    private final SaveTransactionUseCase saveTransactionUseCase;

    private final Predicate<BigDecimal> isSaldoInsuficiente = saldo -> saldo.compareTo(BigDecimal.ZERO) < 0;

    public ProcessTransactionUseCase(GetAccountByAccountNumberUseCase getAccountByNumberUseCase,
                                     GetTransactionStrategyUseCase getTransactionStrategyUseCase,
                                     CalculateFinalBalanceUseCase calculateFinalBalanceUseCase,
                                     SaveAccountUseCase saveAccountUseCase,
                                     SaveTransactionUseCase saveTransactionUseCase) {
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.getTransactionStrategyUseCase = getTransactionStrategyUseCase;
        this.calculateFinalBalanceUseCase = calculateFinalBalanceUseCase;
        this.saveAccountUseCase = saveAccountUseCase;
        this.saveTransactionUseCase = saveTransactionUseCase;
    }

    public Mono<Transaction> apply(CreateTransactionRequest transaction, OperationType operationType) {

        GetByElementRequest accountNumber = new GetByElementRequest(transaction.getAggregateId(),transaction.getAccountNumber());

        return getAccountByNumberUseCase.execute(accountNumber)
                .flatMap(account -> getTransactionStrategyUseCase.apply(account, transaction.getTransactionType(), operationType,transaction.getAmount())
                        .flatMap(strategy -> {
                            BigDecimal finalBalance = calculateFinalBalanceUseCase.apply(
                                    account.getBalance(),
                                    transaction.getAmount(),
                                    strategy.getAmount(),
                                    operationType
                            );

                            if (isSaldoInsuficiente.test(finalBalance)) {
                                return Mono.error(new ConflictException("Insufficient balance for transaction."));
                            }

                            account.setBalance(finalBalance);
                            return saveAccountUseCase.apply(account)
                                    .flatMap(savedAccount -> {
                                        transaction.setAccountId(savedAccount.getId());
                                        transaction.setTransactionCost(strategy.getAmount());
                                        return saveTransactionUseCase.apply(transaction);
                                    });
                        }));
    }
}
