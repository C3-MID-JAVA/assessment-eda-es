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
import ec.com.sofka.appservice.gateway.ITransactionRepository;
import ec.com.sofka.appservice.gateway.dto.TransactionDTO;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class ProcessTransactionUseCase {

    private final GetAccountByAccountNumberUseCase getAccountByNumberUseCase;
    private final GetTransactionStrategyUseCase getTransactionStrategyUseCase;
    private final CalculateFinalBalanceUseCase calculateFinalBalanceUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final ITransactionRepository transactionRepository;

    private final Predicate<BigDecimal> isSaldoInsuficiente = saldo -> saldo.compareTo(BigDecimal.ZERO) < 0;

    public ProcessTransactionUseCase(GetAccountByAccountNumberUseCase getAccountByNumberUseCase,
                                     GetTransactionStrategyUseCase getTransactionStrategyUseCase,
                                     CalculateFinalBalanceUseCase calculateFinalBalanceUseCase,
                                     UpdateAccountUseCase updateAccountUseCase, ITransactionRepository transactionRepository) {
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.getTransactionStrategyUseCase = getTransactionStrategyUseCase;
        this.calculateFinalBalanceUseCase = calculateFinalBalanceUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.transactionRepository = transactionRepository;
    }

    public Mono<TransactionResponse> apply(CreateTransactionRequest cmd, OperationType operationType) {
        GetByElementRequest accountNumberRequest = new GetByElementRequest(cmd.getAggregateId(), cmd.getAccountNumber());
        return getAccountByNumberUseCase.execute(accountNumberRequest)
                .switchIfEmpty(Mono.error(new ConflictException("Account not found")))
                .flatMap(accountResponse -> {
                    // Mapear AccountResponse a Account
                    Account account = mapToAccount(accountResponse);

                    return getTransactionStrategyUseCase.apply(account, cmd.getTransactionType(), operationType, cmd.getAmount())
                            .flatMap(strategy -> {
                                // Calcular el balance final después de la transacción
                                BigDecimal finalBalance = calculateFinalBalanceUseCase.apply(
                                        account.getBalance().getValue(),
                                        cmd.getAmount(),
                                        strategy.getAmount(),
                                        operationType
                                );

                                // Verificar si el saldo es insuficiente
                                if (isSaldoInsuficiente.test(finalBalance)) {
                                    return Mono.error(new ConflictException("Insufficient balance for transaction."));
                                }

                                // Actualizar la cuenta con el nuevo balance
                                UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(
                                        cmd.getAggregateId(),
                                        finalBalance,
                                        account.getAccountNumber().getValue(),
                                        account.getOwner().getValue(),
                                        account.getStatus().getValue()
                                );

                                return updateAccountUseCase.execute(updateAccountRequest)
                                        .flatMap(updatedAccountResponse -> {
                                            // Crear TransactionDTO
                                            TransactionDTO transactionDTO = new TransactionDTO(
                                                    cmd.getAggregateId(),
                                                    cmd.getAmount(),
                                                    strategy.getAmount(),
                                                    LocalDateTime.now(),
                                                    cmd.getTransactionType(),
                                                    cmd.getAccountId()
                                            );

                                            // Guardar la transacción
                                            return transactionRepository.save(transactionDTO)
                                                    .flatMap(savedTransaction -> {
                                                        // Crear TransactionResponse
                                                        TransactionResponse transactionResponse = new TransactionResponse(
                                                                cmd.getAggregateId(),
                                                                savedTransaction.getTransactionId(),
                                                                savedTransaction.getAccountId(),
                                                                savedTransaction.getTransactionCost(),
                                                                savedTransaction.getAmount(),
                                                                savedTransaction.getDate(),
                                                                savedTransaction.getType()
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
}
