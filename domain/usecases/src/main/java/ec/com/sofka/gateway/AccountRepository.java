package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.AccountDTO;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<AccountDTO> findByAcccountNumber(String accountNumber);
    Mono<AccountDTO> save(AccountDTO account);
}
