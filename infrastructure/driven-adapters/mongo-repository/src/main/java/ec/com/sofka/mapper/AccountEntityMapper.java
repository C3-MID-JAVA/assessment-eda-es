package ec.com.sofka.mapper;

import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.aggregate.entities.account.values.AccountId;
import ec.com.sofka.aggregate.entities.account.values.objects.Balance;
import ec.com.sofka.aggregate.entities.account.values.objects.Name;
import ec.com.sofka.aggregate.entities.account.values.objects.NumberAcc;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.generics.utils.AccountStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper {

    public static AccountEntity mapToEntity(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountEntity(
                account.getId().getValue(),
                account.getNumber().getValue(),
                account.getName().getValue(),
                account.getBalance().getValue(),
                account.getStatus().toString()
        );
    }

    public static Account mapToModelFromEntity(AccountEntity accountEntity) {
        if (accountEntity == null) {
            return null;
        }

        return new Account(
                AccountId.of(accountEntity.getId()),
                Balance.of(accountEntity.getBalance()),
                NumberAcc.of(accountEntity.getAccountNumber()),
                Name.of(accountEntity.getName()),
                AccountStatusEnum.valueOf(accountEntity.getStatus())
        );
    }
}
