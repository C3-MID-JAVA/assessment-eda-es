package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.transaction.Transaction;
import reactor.core.publisher.Mono;

public interface ITransactionRepository {
    Mono<TransactionDTO> save(TransactionDTO transaction);
}
