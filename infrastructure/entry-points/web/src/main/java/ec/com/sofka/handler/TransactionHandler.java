package ec.com.sofka.handler;

import ec.com.sofka.appservice.accounts.GetAccountByAccountNumberUseCase;
import ec.com.sofka.appservice.accounts.GetAccountByIdUseCase;
import ec.com.sofka.appservice.transactions.CreateDepositUseCase;
import ec.com.sofka.appservice.transactions.CreateWithDrawalUseCase;
import ec.com.sofka.appservice.transactions.GetTransactionByIdUseCase;
import ec.com.sofka.appservice.transactions.GetTransactionsUseCase;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.mapper.TransactionDTOMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Component
public class TransactionHandler {
    /*
    private final CreateDepositUseCase createDepositUseCase;
    private final CreateWithDrawalUseCase createWithDrawalUseCase;
    private final GetTransactionsUseCase getTransactionsUseCase;
    private final GetTransactionByIdUseCase getTransactionByIdUseCase;
    private final TransactionDTOMapper transactionMapper;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;


    public TransactionHandler(CreateDepositUseCase createDepositUseCase,
                              TransactionDTOMapper transactionMapper,GetAccountByIdUseCase getAccountByIdUseCase,
                              CreateWithDrawalUseCase createWithDrawalUseCase,
                              GetTransactionsUseCase getTransactionsUseCase,
                              GetTransactionByIdUseCase getTransactionByIdUseCase,
                              GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase) {
        this.createDepositUseCase = createDepositUseCase;
        this.transactionMapper = transactionMapper;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.createWithDrawalUseCase = createWithDrawalUseCase;
        this.getTransactionsUseCase = getTransactionsUseCase;
        this.getTransactionByIdUseCase = getTransactionByIdUseCase;
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
    }

    public Mono<TransactionResponseDTO> createDeposit(TransactionRequestDTO transactionRequestDTO) {
        return createDepositUseCase.apply(transactionMapper.transactionRequestToTransaction(transactionRequestDTO))
                .flatMap(transaction -> {
                    return getAccountByIdUseCase.apply(transaction.getAccountId())
                            .map(account -> {
                                return transactionMapper.transactionToTransactionResponse(transaction, account.getBalance(), transactionRequestDTO.getAccountNumber());
                            });
                });
    }

    public Mono<TransactionResponseDTO> createWithDrawal(TransactionRequestDTO transactionRequestDTO) {
        return createWithDrawalUseCase.apply(transactionMapper.transactionRequestToTransaction(transactionRequestDTO))
                .flatMap(transaction -> {
                    return getAccountByIdUseCase.apply(transaction.getAccountId())
                            .map(account -> {
                                return transactionMapper.transactionToTransactionResponse(transaction, account.getBalance(), transactionRequestDTO.getAccountNumber());
                            });
                });
    }


    public Mono<TransactionResponseDTO> getTransactionById(String id) {
        return getTransactionByIdUseCase.apply(id)
                .flatMap(transaction ->
                        getAccountByIdUseCase.apply(transaction.getAccountId())
                                .map(account -> new TransactionResponseDTO(transaction, account.getBalance(), account.getAccountNumber()))  // Mapeamos la respuesta
                                .switchIfEmpty(Mono.just(new TransactionResponseDTO(transaction, BigDecimal.ZERO, "Cuenta desconocida")))  // Si no se encuentra la cuenta
                );
    }


    public Flux<TransactionResponseDTO> getTransactions() {
        return getTransactionsUseCase.apply()
                .flatMap(transaction ->
                        getAccountByIdUseCase.apply(transaction.getAccountId())
                                .map(account -> {
                                    return new TransactionResponseDTO(transaction, account.getBalance(), account.getAccountNumber());
                                })
                                .switchIfEmpty(Mono.just(new TransactionResponseDTO(transaction, BigDecimal.ZERO, "Cuenta desconocida")))
                );
    }
*/

}
