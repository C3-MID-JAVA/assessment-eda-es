package ec.com.sofka.appservice.transactions.transactionprocess;

import ec.com.sofka.account.Account;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.enums.TransactionType;
import ec.com.sofka.strategy.TransaccionStrategy;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class GetTransactionStrategyUseCase {

    private final TransaccionStrategyFactory factory;

    public GetTransactionStrategyUseCase(TransaccionStrategyFactory factory) {
        this.factory = factory;
    }

    public Mono<TransaccionStrategy> apply(Account account, TransactionType type, OperationType operationType, BigDecimal amount) {
        TransaccionStrategy strategy = factory.getStrategy(type, operationType);
        strategy.validate(account, amount);
        return Mono.just(strategy);
    }
}
