package ec.com.sofka.handlers;

import ec.com.sofka.account.CreateAccountUseCase;
import ec.com.sofka.account.GetAllAccountsUseCase;
import ec.com.sofka.data.AccountReqDTO;
import ec.com.sofka.mapper.AccountDTOMapper;
import ec.com.sofka.request.CreateAccountRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAllAccountsUseCase getAllAccountsUseCase;

    public AccountHandler(CreateAccountUseCase createAccountUseCase, GetAllAccountsUseCase getAllAccountsUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAllAccountsUseCase = getAllAccountsUseCase;
    }

    public Mono<AccountReqDTO> createAccount(AccountReqDTO request){

        return createAccountUseCase.execute(
                new CreateAccountRequest(
                        request.getAccountBalance(),
                        request.getAccountNumber(),
                        request.getAccountOwner(),
                        request.getAccountType()
                )
        ).map(res ->new AccountReqDTO(res.getAccountNumber(),
                res.getAccountBalance(), res.getAccountType(),res.getOwnerName()));
    }
    public Flux<AccountReqDTO> getAllAccounts() {

        return getAllAccountsUseCase.execute()
                .map(res ->new AccountReqDTO(res.getAccountNumber(),
                        res.getAccountBalance(), res.getAccountType(),res.getOwnerName()));

    }
}
