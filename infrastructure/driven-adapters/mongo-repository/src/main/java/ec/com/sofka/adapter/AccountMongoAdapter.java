package ec.com.sofka.adapter;

import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.database.account.AccountMongoRepository;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.mapper.AccountMapperEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapter implements AccountRepository {

    private final AccountMongoRepository repository;
    private final MongoTemplate accountMongoTemplate;

    public AccountMongoAdapter(AccountMongoRepository repository, @Qualifier("accountMongoTemplate")  MongoTemplate accountMongoTemplate) {
        this.repository = repository;
        this.accountMongoTemplate = accountMongoTemplate;
    }


    @Override
    public Mono<AccountDTO> findByAcccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).map(AccountMapperEntity::toDTO);
    }

    @Override
    public Mono<AccountDTO> save(AccountDTO account) {
        AccountEntity accountEntity = AccountMapperEntity.toEntity(account);
        return repository.save(accountEntity).map(AccountMapperEntity::toDTO);
    }
}
