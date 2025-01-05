package ec.com.sofka.account;

import ec.com.sofka.account.request.GetAllByUserIdRequest;
import ec.com.sofka.account.responses.AccountResponse;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.generics.interfaces.IUseCase;
import reactor.core.publisher.Flux;

public class GetAllByUserIdUseCase implements IUseCase<GetAllByUserIdRequest, AccountResponse> {

    private final AccountRepository accountRepository;

    public GetAllByUserIdUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Flux<AccountResponse> execute(GetAllByUserIdRequest request) {
        return accountRepository.getAllByUserId(request.getUserId())
                .map(accountDTO -> new AccountResponse(
                        accountDTO.getCustomerId(),
                        accountDTO.getAccountNumber(),
                        accountDTO.getBalance(),
                        accountDTO.getUserId()
                ));
    }

}
