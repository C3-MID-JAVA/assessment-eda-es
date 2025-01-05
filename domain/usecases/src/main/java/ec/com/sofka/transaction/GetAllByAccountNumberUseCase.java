package ec.com.sofka.transaction;

import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.transaction.request.GetAllByAccountNumberRequest;
import ec.com.sofka.transaction.responses.TransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAllByAccountNumberUseCase implements IUseCase<GetAllByAccountNumberRequest, TransactionResponse> {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public GetAllByAccountNumberUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Flux<TransactionResponse> execute(GetAllByAccountNumberRequest cmd) {
        return accountRepository.findByAccountNumber(cmd.getAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
                .flatMapMany(accountDTO -> {
                    return transactionRepository.getAllByAccountId(accountDTO.getId())
                            .map(transactionDTO -> new TransactionResponse(
                                    transactionDTO.getCustomerId(),
                                    transactionDTO.getAmount(),
                                    transactionDTO.getFee(),
                                    transactionDTO.getNetAmount(),
                                    transactionDTO.getType(),
                                    transactionDTO.getTimestamp(),
                                    transactionDTO.getAccountId()
                            ));
                });
    }
}
