package ec.com.sofka;

import ec.com.sofka.data.TransactionEntity;
import ec.com.sofka.database.account.ITransactionRepository;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TransactionMongoAdapter implements TransactionRepository {
    private final ITransactionRepository repository;
    private final ReactiveMongoTemplate accountMongoTemplate;

    public TransactionMongoAdapter(ITransactionRepository repository, @Qualifier("accountMongoTemplate") ReactiveMongoTemplate accountMongoTemplate) {
        this.repository = repository;
        this.accountMongoTemplate = accountMongoTemplate;
    }


    @Override
    public Mono<TransactionDTO> save(TransactionDTO transactionDTO) {
        TransactionEntity transaction = TransactionMapper.toEntity(transactionDTO);
        return repository.save(transaction)
                .map(TransactionMapper::toDTO);
    }
}
