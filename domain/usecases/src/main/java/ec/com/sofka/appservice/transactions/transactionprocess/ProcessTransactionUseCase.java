package ec.com.sofka.appservice.transactions.transactionprocess;

import ec.com.sofka.ConflictException;
import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.Balance;
import ec.com.sofka.account.values.objects.NumberAcc;
import ec.com.sofka.account.values.objects.Owner;
import ec.com.sofka.account.values.objects.Status;
import ec.com.sofka.appservice.accounts.UpdateAccountUseCase;
import ec.com.sofka.appservice.data.request.CreateTransactionRequest;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.data.request.UpdateAccountRequest;
import ec.com.sofka.appservice.data.response.AccountResponse;
import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.appservice.accounts.GetAccountByAccountNumberUseCase;
import ec.com.sofka.enums.OperationType;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class ProcessTransactionUseCase {

    private final GetAccountByAccountNumberUseCase getAccountByNumberUseCase;
    private final GetTransactionStrategyUseCase getTransactionStrategyUseCase;
    private final CalculateFinalBalanceUseCase calculateFinalBalanceUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final SaveTransactionUseCase saveTransactionUseCase;

    private final Predicate<BigDecimal> isSaldoInsuficiente = saldo -> saldo.compareTo(BigDecimal.ZERO) < 0;

    public ProcessTransactionUseCase(GetAccountByAccountNumberUseCase getAccountByNumberUseCase,
                                     GetTransactionStrategyUseCase getTransactionStrategyUseCase,
                                     CalculateFinalBalanceUseCase calculateFinalBalanceUseCase,
                                     UpdateAccountUseCase updateAccountUseCase,
                                     SaveTransactionUseCase saveTransactionUseCase) {
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.getTransactionStrategyUseCase = getTransactionStrategyUseCase;
        this.calculateFinalBalanceUseCase = calculateFinalBalanceUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.saveTransactionUseCase = saveTransactionUseCase;
    }
/*
    public Mono<Transaction> apply(CreateTransactionRequest transaction, OperationType operationType) {

        // Obtener la cuenta mediante el número de cuenta
        GetByElementRequest accountNumber = new GetByElementRequest(transaction.getAggregateId(), transaction.getAccountNumber());

        return getAccountByNumberUseCase.execute(accountNumber)
                .flatMap(accountResponse -> {
                    // Convertir el AccountResponse a Account
                    Account account = mapToAccount(accountResponse);

                    // Aplicar la estrategia de transacción (validación)
                    return getTransactionStrategyUseCase.apply(account, transaction.getTransactionType(), operationType, transaction.getAmount())
                            .flatMap(strategy -> {
                                // Calcular el balance final después de la transacción
                                BigDecimal finalBalance = calculateFinalBalanceUseCase.apply(
                                        account.getBalance().getValue(),
                                        transaction.getAmount(),
                                        strategy.getAmount(),
                                        operationType
                                );

                                // Validar si el saldo es suficiente
                                if (isSaldoInsuficiente.test(finalBalance)) {
                                    return Mono.error(new ConflictException("Insufficient balance for transaction."));
                                }

                                // Actualizar el balance de la cuenta
                                account.setBalance(finalBalance);

                                // Guardar la cuenta con el balance actualizado
                                return saveAccountUseCase.apply(account)
                                        .flatMap(savedAccount -> {
                                            // Establecer la información de la transacción
                                            transaction.setAccountId(savedAccount.getId());
                                            transaction.setTransactionCost(strategy.getAmount());

                                            // Guardar la transacción
                                            return saveTransactionUseCase.apply(transaction);
                                        });
                            });
                });
    }
*/

    public Mono<TransactionResponse> apply(CreateTransactionRequest transaction, OperationType operationType) {
        GetByElementRequest accountNumberRequest = new GetByElementRequest(transaction.getAggregateId(), transaction.getAccountNumber());

        return getAccountByNumberUseCase.execute(accountNumberRequest)
                .switchIfEmpty(Mono.error(new ConflictException("Account not found")))
                .flatMap(accountResponse -> {
                    // Mapeo de AccountResponse a Account
                    Account account = mapToAccount(accountResponse);

                    return getTransactionStrategyUseCase.apply(account, transaction.getTransactionType(), operationType, transaction.getAmount())
                            .flatMap(strategy -> {
                                // Calcular el balance final después de la transacción
                                BigDecimal finalBalance = calculateFinalBalanceUseCase.apply(
                                        account.getBalance().getValue(),
                                        transaction.getAmount(),
                                        strategy.getAmount(),
                                        operationType
                                );

                                // Verificar si el saldo es insuficiente
                                if (isSaldoInsuficiente.test(finalBalance)) {
                                    return Mono.error(new ConflictException("Insufficient balance for transaction."));
                                }

                                // Actualizar el balance de la cuenta
                                account.setBalance(Balance.of(finalBalance));

                                // Crear solicitud para actualizar la cuenta
                                UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(
                                        transaction.getAggregateId(),
                                        account.getId().getValue(),
                                        finalBalance,
                                        account.getAccountNumber().getValue(),
                                        account.getOwner().getValue(),
                                        account.getStatus().getValue()
                                );

                                // Actualizar la cuenta con el nuevo balance
                                return updateAccountUseCase.execute(updateAccountRequest)
                                        .flatMap(updatedAccountResponse -> {
                                            // Establecer la información de la transacción
                                            transaction.setAccountId(updatedAccountResponse.getAccountId());
                                            transaction.setTransactionCost(strategy.getAmount());

                                            // Guardar la transacción
                                            return saveTransactionUseCase.apply(transaction)
                                                    .flatMap(savedTransaction -> {
                                                        // Crear la respuesta de la transacción
                                                        TransactionResponse transactionResponse = new TransactionResponse(
                                                                savedTransaction.getId(),
                                                                savedTransaction.getAmount(),
                                                                savedTransaction.getTransactionCost(),
                                                                savedTransaction.getDate().getValue(),
                                                                savedTransaction.getType(),
                                                                savedTransaction.getAccountId()
                                                        );
                                                        return Mono.just(transactionResponse);
                                                    });
                                        });
                            });
                });
    }



    private Account mapToAccount(AccountResponse accountResponse) {
        Balance balance = Balance.of(accountResponse.getBalance());
        AccountId accountId = AccountId.of(accountResponse.getAccountId());
        NumberAcc numberAcc = NumberAcc.of(accountResponse.getAccountNumber());
        Owner owner = Owner.of(accountResponse.getCustomerId());
        Status status = Status.of(accountResponse.getStatus());

        return new Account(accountId, balance, numberAcc, owner, status);
    }
    /*
    private Account mapToAccount(AccountResponse accountResponse) {
        // Aquí mapeas el DTO AccountResponse a la entidad Account
        Balance balance = Balance.of(accountResponse.getBalance());
        // Suponiendo que tienes las demás propiedades necesarias para crear el objeto Account
        AccountId accountId = AccountId.of(accountResponse.getAccountId());
        NumberAcc numberAcc = NumberAcc.of(accountResponse.getAccountNumber());
        Owner owner = Owner.of(accountResponse.getCustomerId()); // Suponiendo que Owner se crea con el customerId
        Status status = Status.of(accountResponse.getStatus()); // Suponiendo que Status se crea con el status

        return new Account(accountId, balance, numberAcc, owner, status);
    }*/
}
