package ec.com.sofka.adapters;

import ec.com.sofka.database.account.IAccountMongoRepository;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapter implements IAccountRepository {

    private final IAccountMongoRepository accountMongoRepository;
    private final ReactiveMongoTemplate accountMongoTemplate;

    public AccountMongoAdapter(IAccountMongoRepository accountMongoRepository, @Qualifier("accountMongoTemplate")  ReactiveMongoTemplate accountMongoTemplate) {
        this.accountMongoRepository = accountMongoRepository;
        this.accountMongoTemplate = accountMongoTemplate;
    }

    /*@Override
    public AccountDTO findByAcccountId(String id) {
        AccountEntity found = repository.findById(id).get();

        return AccountMapper.toDTO(found);
    }*/

    @Override
    public Mono<AccountDTO> findByAccountNumber(String accountNumber) {
        return accountMongoRepository.findByAccountNumber(accountNumber).map(AccountMapper::toDTO);
    }

    @Override
    public Flux<AccountDTO> findAllAccounts() {
        return null;
    }

    @Override
    public Mono<AccountDTO> save(AccountDTO account) {
        System.out.println("Saving account: " + account.getAccountNumber());
        return accountMongoRepository.save(AccountMapper.toEntity(account))
                .map(AccountMapper::toDTO);
    }

}
