package ec.com.sofka.appservice.transactions.transactionprocess;

import ec.com.sofka.enums.OperationType;

import java.math.BigDecimal;
public class CalculateFinalBalanceUseCase {

    public BigDecimal apply(BigDecimal saldoActual, BigDecimal monto, BigDecimal costo, OperationType tipoOperacion) {
        return tipoOperacion == OperationType.DEPOSIT
                ? saldoActual.add(monto).subtract(costo)
                : saldoActual.subtract(monto.add(costo));
    }
}