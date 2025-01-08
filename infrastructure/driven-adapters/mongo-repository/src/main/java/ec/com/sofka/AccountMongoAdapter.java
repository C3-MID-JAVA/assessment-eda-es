package ec.com.sofka;

import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.database.IMongoAccountRepository;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.mapper.AccountEntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapter implements IAccountRepository {

    private final IMongoAccountRepository repository;
    private final ReactiveMongoTemplate accountMongoTemplate;

    public AccountMongoAdapter(IMongoAccountRepository repository, ReactiveMongoTemplate accountMongoTemplate) {
        this.repository = repository;
        this.accountMongoTemplate = accountMongoTemplate;
    }

    @Override
    public Flux<AccountDTO> findAll() {
        return repository.findAll() // Llamada correcta al método findAll del repositorio.
                .map(AccountEntityMapper::mapToDTO); // Mapear cada entidad a un DTO.
    }

    @Override
    public Mono<AccountDTO> findByAccountId(String id) {
        return repository.findById(id)
                .map(AccountEntityMapper::mapToDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found with ID: " + id)));
    }

    @Override
    public Mono<AccountDTO> findByNumber(String number) {
        return repository.findByAccountNumber(number)
                .map(AccountEntityMapper::mapToDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found with number: " + number)));
    }

    @Override
    public Mono<AccountDTO> save(AccountDTO account) {
        return Mono.just(account)
                .map(AccountEntityMapper::mapToEntity)
                .flatMap(repository::save)
                .map(AccountEntityMapper::mapToDTO);
    }

    @Override
    public Mono<AccountDTO> update(AccountDTO account) {
        return repository.findById(account.getId())
                .flatMap(found -> {
                    AccountEntity updated = new AccountEntity(
                            found.getId(),
                            found.getAccountId(),
                            account.getName(),
                            account.getAccountNumber(),
                            found.getBalance(),
                            found.getStatus()
                    );
                    return repository.save(updated);
                })
                .map(AccountEntityMapper::mapToDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found for update with ID: " + account.getId())));
    }
}
