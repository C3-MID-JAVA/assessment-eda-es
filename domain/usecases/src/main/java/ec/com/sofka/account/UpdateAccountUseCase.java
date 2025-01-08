package ec.com.sofka.account;



import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.dto.AccountDTO;
import reactor.core.publisher.Mono;


public class UpdateAccountUseCase {
    private final IAccountRepository accountRepository;


    public UpdateAccountUseCase(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<AccountDTO> apply(AccountDTO account) {
        return accountRepository.save(account);
    }
}
