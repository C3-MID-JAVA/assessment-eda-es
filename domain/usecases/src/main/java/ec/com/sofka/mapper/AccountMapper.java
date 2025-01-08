package ec.com.sofka.mapper;

import ec.com.sofka.aggregate.entities.account.Account;
import ec.com.sofka.aggregate.entities.account.values.AccountId;
import ec.com.sofka.aggregate.entities.account.values.objects.Balance;
import ec.com.sofka.aggregate.entities.account.values.objects.Name;
import ec.com.sofka.aggregate.entities.account.values.objects.NumberAcc;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.responses.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    /**
     * Maps an AccountDTO to an Account model.
     *
     * @param accountDTO the DTO to map from
     * @return the mapped Account model
     */
    public static Account mapToModelFromDTO(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        return new Account(
                AccountId.of(accountDTO.getId()),
                Balance.of(accountDTO.getBalance()),
                NumberAcc.of(accountDTO.getAccountNumber()),
                Name.of(accountDTO.getName()),
                accountDTO.getStatus()
        );
    }

    /**
     * Maps an Account model to an AccountDTO.
     *
     * @param account the model to map from
     * @return the mapped AccountDTO
     */
    public static AccountDTO mapToDTOFromModel(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountDTO(
                account.getId().getValue(),
                account.getName().getValue(),
                account.getNumber().getValue(),
                account.getBalance().getValue(),
                account.getStatus()
        );
    }

    /**
     * Maps an Account model to an AccountResponse.
     *
     * @param account the model to map from
     * @param customerId the ID of the customer
     * @return the mapped AccountResponse
     */
    public static AccountResponse mapToResponseFromModel(Account account, String customerId) {
        if (account == null) {
            return null;
        }

        return new AccountResponse(
                customerId,
                account.getId().getValue(),
                account.getNumber().getValue(),
                account.getName().getValue(),
                account.getBalance().getValue(),
                account.getStatus()
        );
    }

    /**
     * Maps an AccountDTO to an AccountResponse.
     *
     * @param accountDTO the DTO to map from
     * @param customerId the ID of the customer
     * @return the mapped AccountResponse
     */
    public static AccountResponse mapToResponseFromDTO(AccountDTO accountDTO, String customerId) {
        if (accountDTO == null) {
            return null;
        }

        return new AccountResponse(
                customerId,
                accountDTO.getId(),
                accountDTO.getAccountNumber(),
                accountDTO.getName(),
                accountDTO.getBalance(),
                accountDTO.getStatus()
        );
    }
}
