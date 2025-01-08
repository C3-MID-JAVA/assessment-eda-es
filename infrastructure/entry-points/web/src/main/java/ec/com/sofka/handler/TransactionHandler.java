package ec.com.sofka.handler;

import ec.com.sofka.appservice.accounts.GetAccountByIdUseCase;
import ec.com.sofka.appservice.data.request.GetByElementRequest;
import ec.com.sofka.appservice.transactions.CreateDepositUseCase;
import ec.com.sofka.appservice.transactions.CreateWithDrawalUseCase;
import ec.com.sofka.appservice.transactions.GetTransactionByAccNumberUseCase;
import ec.com.sofka.appservice.transactions.GetTransactionsUseCase;
import ec.com.sofka.data.AccountReqByIdDTO;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.mapper.TransactionDTOMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class TransactionHandler {

    private final CreateDepositUseCase createDepositUseCase;
    private final CreateWithDrawalUseCase createWithDrawalUseCase;
    private final GetTransactionsUseCase getTransactionsUseCase;
    private final GetTransactionByAccNumberUseCase getTransactionByIdUseCase;
    private final TransactionDTOMapper transactionMapper;
    private final GetAccountByIdUseCase getAccountByIdUseCase;


    public TransactionHandler(CreateDepositUseCase createDepositUseCase,
                              TransactionDTOMapper transactionMapper,GetAccountByIdUseCase getAccountByIdUseCase,
                              CreateWithDrawalUseCase createWithDrawalUseCase,
                              GetTransactionsUseCase getTransactionsUseCase,
                              GetTransactionByAccNumberUseCase getTransactionByIdUseCase) {
        this.createDepositUseCase = createDepositUseCase;
        this.transactionMapper = transactionMapper;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.createWithDrawalUseCase = createWithDrawalUseCase;
        this.getTransactionsUseCase = getTransactionsUseCase;
        this.getTransactionByIdUseCase = getTransactionByIdUseCase;
    }

    public Mono<TransactionResponseDTO> createDeposit(TransactionRequestDTO transactionRequestDTO) {
        return createDepositUseCase.execute(transactionMapper.toCreateTransactionRequest(transactionRequestDTO))
                .flatMap(transaction -> {
                    GetByElementRequest request = new GetByElementRequest(transaction.getCustomerId(), transaction.getAccountId());
                    return getAccountByIdUseCase.execute(request)
                            .map(account -> {
                                return transactionMapper.toTransactionResponseDTO(transaction);
                            });
                });
    }

    public Mono<TransactionResponseDTO> createWithDrawal(TransactionRequestDTO transactionRequestDTO) {
        return createWithDrawalUseCase.execute(transactionMapper.toCreateTransactionRequest(transactionRequestDTO))
                .flatMap(transaction -> {
                    GetByElementRequest request = new GetByElementRequest(transaction.getCustomerId(), transaction.getAccountId());
                    return getAccountByIdUseCase.execute(request)
                            .map(account -> {
                                return transactionMapper.toTransactionResponseDTO(transaction);
                            });
                });
    }



    public Mono<TransactionResponseDTO> getTransactionByAccountNumber(AccountReqByIdDTO req) {
        GetByElementRequest request = new GetByElementRequest(req.getCustomerId(), req.getAccountNumber());

        return getTransactionByIdUseCase.execute(request)
                .flatMap(transactionResponse ->
                        getAccountByIdUseCase.execute(new GetByElementRequest(transactionResponse.getCustomerId(),
                                        transactionResponse.getAccountId()))
                                .map(accountResponse -> TransactionDTOMapper.toTransactionResponseDTO(transactionResponse))

                );
    }

    public Flux<TransactionResponseDTO> getTransactions() {
        return getTransactionsUseCase.apply()
                .flatMap(transaction ->
                        getAccountByIdUseCase.execute(new GetByElementRequest(transaction.getCustomerId(), transaction.getAccountId()))
                                .map(accountResponse -> TransactionDTOMapper.toTransactionResponseDTO(transaction))
                                .switchIfEmpty(Mono.just(TransactionDTOMapper.toTransactionResponseDTO(transaction)))
                );
    }


}
