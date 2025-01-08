/*package ec.com.sofka;


import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.database.account.IMongoRepository;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountMongoAdapter implements AccountRepository {

    private final IMongoRepository repository;
    private final MongoTemplate accountMongoTemplate;

    public AccountMongoAdapter(IMongoRepository repository, @Qualifier("accountMongoTemplate")  MongoTemplate accountMongoTemplate) {
        this.repository = repository;
        this.accountMongoTemplate = accountMongoTemplate;
    }

    @Override
    public AccountDTO findByAcccountId(String id) {
        AccountEntity found = repository.findById(id).get();

        return AccountMapper.toDTO(found);
    }

    @Override
    public AccountDTO save(AccountDTO account) {
        AccountEntity a = AccountMapper.toEntity(account);
        AccountEntity saved = repository.save(a);
        return AccountMapper.toDTO(saved);
    }
}

 */
package ec.com.sofka;

import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.database.account.IMongoRepository;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapter implements AccountRepository {

    private final IMongoRepository repository;
    private final ReactiveMongoTemplate accountMongoTemplate;

    public AccountMongoAdapter(IMongoRepository repository, @Qualifier("accountMongoTemplate") ReactiveMongoTemplate accountMongoTemplate) {
        this.repository = repository;
        this.accountMongoTemplate = accountMongoTemplate;
    }

    @Override
    public Mono<AccountDTO> findByAcccountId(String id) {
        return repository.findById(id)
                .map(AccountMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> save(AccountDTO account) {
        AccountEntity a = AccountMapper.toEntity(account);
        return repository.save(a)
                .map(AccountMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> findByNumber(String number) {
        return repository.findByAccountNumber(number)
                .doOnNext(result -> {
                    System.out.println("Result: " + result);
                })
                        .map( AccountMapper::toDTO);
    }

    @Override
    public Flux<AccountDTO> findAll() {
        return repository.findAll()
                .map(AccountMapper::toDTO);
    }

    //@Override
   // public Mono<AccountDTO> update(AccountDTO account) {
      //  AccountEntity found = repository.findByAccountId(AccountMapper.toEntity(account).getAccountId());

        //return found != null ?
//                AccountMapper.toDTO(repository.save(
//                        new AccountEntity(
//                                found.getId(),
//                                found.getAccountId(),
//                                account.getBalance(),
//                                account.getOwner(),
//                                account.getAccountNumber(),
//                                account.getIdUser(),
//                                found.getStatus()
//                        )
//                )) : null;
//    }


    @Override
    public Mono<AccountDTO> update(AccountDTO account) {
        return repository.findByAccountId(AccountMapper.toEntity(account).getAccountId())
                .flatMap(found -> {
                    if (found != null) {
                        AccountEntity updatedEntity = new AccountEntity(
                                found.getId(),
                                found.getAccountId(),
                                account.getBalance(),
                                account.getOwner(),
                                account.getAccountNumber(),
                                account.getIdUser(),
                                found.getStatus()
                        );
                        return repository.save(updatedEntity)
                                .map(AccountMapper::toDTO);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
