package ec.com.sofka.entities.accountbank;

import ec.com.sofka.entities.accountbank.values.AccountBankId;
import ec.com.sofka.entities.accountbank.values.objects.AccountHolder;
import ec.com.sofka.entities.accountbank.values.objects.AccountNumber;
import ec.com.sofka.entities.accountbank.values.objects.GeneralBalance;
import ec.com.sofka.generics.utils.Entity;

public class AccountBank extends Entity<AccountBankId> {

    private AccountHolder accountHolder;
    private AccountNumber accountNumber;
    private GeneralBalance generalBalance;

    public AccountBank(AccountBankId id,
                       AccountHolder accountHolder,
                       AccountNumber accountNumber,
                       GeneralBalance generalBalance
    ) {
        super(id);
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.generalBalance = generalBalance;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public GeneralBalance getGeneralBalance() {
        return generalBalance;
    }
}
