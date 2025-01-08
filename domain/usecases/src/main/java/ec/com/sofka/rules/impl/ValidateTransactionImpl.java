package ec.com.sofka.rules.impl;

import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.rules.IValidateTransaction;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class ValidateTransactionImpl implements IValidateTransaction {

    @Override
    public Mono<TransactionDTO> validateTransaction(TransactionDTO transaction, String accountAggregateId) {
        if (transaction == null || accountAggregateId == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new IllegalArgumentException("Transacción inválida o datos incompletos"));
        }

        if (!transaction.getAccount().getId().equals(accountAggregateId)) {
            return Mono.error(new IllegalArgumentException("La transacción no pertenece al agregado proporcionado"));
        }

        return Mono.just(transaction);
    }
}
