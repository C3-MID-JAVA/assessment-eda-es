package ec.com.sofka.rules;

import ec.com.sofka.Transaction;
import ec.com.sofka.TransactionType;
import ec.com.sofka.rules.impl.BalanceCalculatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BalanceCalculatorImplTest {

    private final BalanceCalculatorImpl balanceCalculator = new BalanceCalculatorImpl();

    @Test
    public void testCalculate_WithDiscount() {
        Transaction transaction = mock(Transaction.class);
        TransactionType transactionType = mock(TransactionType.class);

        when(transaction.getValue()).thenReturn(new BigDecimal("100"));
        when(transactionType.getValue()).thenReturn(new BigDecimal("10"));
        when(transaction.getTransactionType()).thenReturn(transactionType);
        when(transactionType.getDiscount()).thenReturn(true);

        BigDecimal currentBalance = new BigDecimal("500");

        BigDecimal result = balanceCalculator.calculate(transaction, currentBalance);

        assertEquals(new BigDecimal("390"), result);
    }

    @Test
    public void testCalculate_WithoutDiscount() {
        Transaction transaction = mock(Transaction.class);
        TransactionType transactionType = mock(TransactionType.class);

        when(transaction.getValue()).thenReturn(new BigDecimal("100"));
        when(transactionType.getValue()).thenReturn(new BigDecimal("0"));
        when(transaction.getTransactionType()).thenReturn(transactionType);
        when(transactionType.getDiscount()).thenReturn(false);

        BigDecimal currentBalance = new BigDecimal("500");

        BigDecimal result = balanceCalculator.calculate(transaction, currentBalance);

        assertEquals(new BigDecimal("600"), result);
    }

}