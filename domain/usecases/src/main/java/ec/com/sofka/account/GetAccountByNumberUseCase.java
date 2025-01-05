package ec.com.sofka.account;

import ec.com.sofka.account.request.GetAccountByNumberRequest;
import ec.com.sofka.account.responses.AccountResponse;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase implements IUseCase<GetAccountByNumberRequest, AccountResponse> {
    private final AccountRepository accountRepository;

    public GetAccountByNumberUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<AccountResponse> execute(GetAccountByNumberRequest cmd) {
        return accountRepository.findByAccountNumber(cmd.getAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
                .map(accountDTO -> new AccountResponse(
                        accountDTO.getCustomerId(),
                        accountDTO.getAccountNumber(),
                        accountDTO.getBalance(),
                        accountDTO.getUserId()
                ));
    }
}
