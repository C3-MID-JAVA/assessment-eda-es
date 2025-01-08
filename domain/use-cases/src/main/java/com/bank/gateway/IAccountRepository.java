package com.bank.gateway;

import com.bank.account.Account;
import com.bank.gateway.dto.AccountDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountRepository {
    Flux<AccountDTO> findAllAccounts();
    Mono<AccountDTO> findAccountById(String id);
    Mono<AccountDTO> createAccount(AccountDTO account);
}