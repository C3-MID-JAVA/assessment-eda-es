package ec.com.sofka.appservice.gateway;

import ec.com.sofka.account.Account;
import ec.com.sofka.appservice.gateway.dto.AccountDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountRepository {
   Mono<AccountDTO> findByAccountNumber(String accountNumber);
    Mono<AccountDTO> save(AccountDTO cuenta);
    //Flux<Account> findAll();
   // Mono<Account> findById(String id);
}
