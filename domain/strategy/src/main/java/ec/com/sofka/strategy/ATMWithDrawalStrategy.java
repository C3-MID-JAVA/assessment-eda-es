package ec.com.sofka.strategy;

import ec.com.sofka.account.Account;
import ec.com.sofka.ConflictException;

import java.math.BigDecimal;

public class ATMWithDrawalStrategy implements TransaccionStrategy{
    private static final BigDecimal CHARGE = BigDecimal.valueOf(1.0);

    @Override
    public void validate(Account account, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ConflictException("The amount for ATM withdrawal must be greater than 0.");
        }

        account.getBalance().ensureSufficientBalanceWithCost(amount, CHARGE);
    }

    @Override
    public BigDecimal getAmount() {
        return CHARGE;
    }
}