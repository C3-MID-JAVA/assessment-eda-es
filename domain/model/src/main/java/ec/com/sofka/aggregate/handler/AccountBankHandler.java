package ec.com.sofka.aggregate.handler;

import ec.com.sofka.entities.accountbank.AccountBank;
import ec.com.sofka.entities.accountbank.values.AccountBankId;
import ec.com.sofka.entities.accountbank.values.objects.AccountHolder;
import ec.com.sofka.entities.accountbank.values.objects.AccountNumber;
import ec.com.sofka.entities.accountbank.values.objects.GeneralBalance;
import ec.com.sofka.aggregate.agregates.AccountBankAgregate;
import ec.com.sofka.aggregate.events.accountBank.AccountBankCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;

public class AccountBankHandler extends DomainActionsContainer {
    public AccountBankHandler(AccountBankAgregate accountBankAgregate) {
        addDomainActions((AccountBankCreated event) -> {
            AccountBank accountBank = new AccountBank(
                    new AccountBankId(),
                    AccountHolder.of(event.getAccountHolder()),
                    AccountNumber.of(event.getAccountNumber()),
                    GeneralBalance.of(event.getGeneralBalance())
            );
        });
    }
}
