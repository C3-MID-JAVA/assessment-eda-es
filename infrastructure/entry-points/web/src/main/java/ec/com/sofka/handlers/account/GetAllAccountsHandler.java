package ec.com.sofka.handlers.account;

import ec.com.sofka.cases.account.GetAllAccountsUseCase;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.mapper.AccountModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllAccountsHandler {
    private final GetAllAccountsUseCase getAllAccountsUseCase;

    public GetAllAccountsHandler(GetAllAccountsUseCase getAllAccountsUseCase) {
        this.getAllAccountsUseCase = getAllAccountsUseCase;
    }

    public Flux<AccountResponseDTO> getAllAccounts() {
        return getAllAccountsUseCase.apply()
                .map(AccountModelMapper::mapToDTO);
    }

}
