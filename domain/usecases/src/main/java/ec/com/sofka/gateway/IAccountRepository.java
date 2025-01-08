package ec.com.sofka.gateway;


import ec.com.sofka.account.Account;
import ec.com.sofka.gateway.dto.AccountDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IAccountRepository {
    Mono<AccountDTO> findByAccountNumber(String accountNumber);
    Flux<AccountDTO> findAllAccounts();
    Mono<AccountDTO> save(AccountDTO account);
}
