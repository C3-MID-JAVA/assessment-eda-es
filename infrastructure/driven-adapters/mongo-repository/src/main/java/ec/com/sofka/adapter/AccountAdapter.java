package ec.com.sofka.adapter;

import ec.com.sofka.account.Account;
import ec.com.sofka.appservice.gateway.IAccountRepository;
import ec.com.sofka.appservice.gateway.dto.AccountDTO;
import ec.com.sofka.database.account.IMongoAccountRepository;
import ec.com.sofka.mapper.AccountMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements IAccountRepository {

    private final IMongoAccountRepository repository;

    public AccountAdapter(IMongoAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<AccountDTO> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .map(AccountMapper::entityToDTO
                );
    }

    @Override
    public Mono<AccountDTO> save(AccountDTO account) {
        return repository.save(AccountMapper.DtoToEntity(account)).map(AccountMapper::entityToDTO);
    }
/*
    @Override
    public Flux<AccountDTO> findAll() {
        return repository.findAll().map(AccountMapper::entityToDTO);
    }*/
/*
    @Override
    public Mono<AccountDTO> findById(String id) {
        return repository.findById(id).map(AccountMapper::entityToDTO);
    }*/
}
