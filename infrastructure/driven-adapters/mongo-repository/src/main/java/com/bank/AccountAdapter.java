package com.bank;

import com.bank.database.bank.IAccountMongoRepository;
import com.bank.gateway.IAccountRepository;
import com.bank.gateway.dto.AccountDTO;
import com.bank.gateway.dto.mapper.AccountDTOMapper;
import com.bank.mapper.AccountEntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements IAccountRepository {

    private final IAccountMongoRepository repository;
    private final ReactiveMongoTemplate bankMongoTemplate;

    public AccountAdapter(IAccountMongoRepository repository, @Qualifier("bankMongoTemplate") ReactiveMongoTemplate bankMongoTemplate) {
        this.repository = repository;
        this.bankMongoTemplate = bankMongoTemplate;
    }

    @Override
    public Flux<AccountDTO> findAllAccounts() {
        return repository.findAll().map(AccountEntityMapper::toAccount).map(AccountDTOMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> findAccountById(String id) {
        return repository.findById(id).map(AccountEntityMapper::toAccount).map(AccountDTOMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> createAccount(AccountDTO dto) {
        var acc = AccountEntityMapper.toAccountEntity(AccountDTOMapper.toModel(dto));
        return repository.insert(acc).map(AccountEntityMapper::toAccount).map(AccountDTOMapper::toDTO);
    }
}
