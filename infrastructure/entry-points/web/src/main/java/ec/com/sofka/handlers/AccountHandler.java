package ec.com.sofka.handlers;
import ec.com.sofka.CreateAccountUseCase;
import ec.com.sofka.GetAccountByNumberUseCase;
import ec.com.sofka.GetAllAccountsUseCase;
import ec.com.sofka.UpdateAccountUseCase;
import ec.com.sofka.data.RequestDTO;
import ec.com.sofka.data.ResponseDTO;
import ec.com.sofka.request.account.CreateAccountRequest;
import ec.com.sofka.request.account.GetAccountRequest;
import ec.com.sofka.request.account.UpdateAccountRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final GetAccountByNumberUseCase getAccountByNumberUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;

    public AccountHandler(CreateAccountUseCase createAccountUseCase, GetAllAccountsUseCase getAllAccountsUseCase, GetAccountByNumberUseCase getAccountByNumberUseCase, UpdateAccountUseCase updateAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAllAccountsUseCase = getAllAccountsUseCase;
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
    }

    public Mono<ResponseDTO> createAccount(RequestDTO request) {
        return createAccountUseCase.execute(
                new CreateAccountRequest(
                        request.getBalance(),
                        request.getAccount(),
                        request.getCustomer(),
                        request.getIdUser()
                )
        ).map(response -> new ResponseDTO(response.getCustomerId(), response.getAccountId(),response.getName(),response.getStatus()));
    }

    public Flux<ResponseDTO> getAllAccounts() {
        return getAllAccountsUseCase.get()
                .map(accountResponse -> new ResponseDTO(
                        accountResponse.getCustomerId(),
                        accountResponse.getAccountId(),
                        accountResponse.getName(),
                        accountResponse.getStatus(),
                        accountResponse.getBalance(),
                        accountResponse.getAccountNumber()
                ));
    }

    public Mono<ResponseDTO> getAccountByNumber(RequestDTO request) {
        return getAccountByNumberUseCase.execute(
                        new GetAccountRequest(
                                request.getCustomerId(),
                                request.getAccount()
                        ))
                .map(response -> new ResponseDTO(
                        response.getCustomerId(),
                        response.getAccountId(),
                        response.getAccountNumber(),
                        response.getStatus()
                ))
                .switchIfEmpty(Mono.empty());
    }
/*
    public Mono<ResponseDTO> updateAccount(RequestDTO request){
        var response = updateAccountUseCase.execute(
                new UpdateAccountRequest(
                        request.getCustomerId(),
                        request.getBalance(),
                        request.getAccount(),
                        request.getCustomer(),
                        request.getIdUser(),
                        request.getStatus()
                ));

        return new ResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getStatus()
        );
    }*/
public Mono<ResponseDTO> updateAccount(RequestDTO request) {
    return updateAccountUseCase.execute(
                    new UpdateAccountRequest(
                            request.getCustomerId(),
                            request.getBalance(),
                            request.getAccount(),
                            request.getCustomer(),
                            request.getIdUser(),
                            request.getStatus()
                    ))
            .map(response -> new ResponseDTO(
                    response.getCustomerId(),
                    response.getAccountId(),
                    response.getName(),
                    response.getStatus()
            ));
}
}