package ec.com.sofka.mapper;


import ec.com.sofka.data.AccountReqDTO;
import ec.com.sofka.gateway.dto.AccountDTO;

public class AccountDTOMapper {
    public static AccountDTO toAccountDTO(AccountReqDTO accountResponseDTO) {

        if (accountResponseDTO == null) {
            return null;
        }
        System.out.println("accountNumber " + accountResponseDTO.getAccountNumber());

        return new AccountDTO( accountResponseDTO.getAccountBalance(),accountResponseDTO.getAccountOwner(),
                accountResponseDTO.getAccountNumber(), accountResponseDTO.getAccountType());
    }

    public static AccountReqDTO toAccountReqDTO(AccountDTO account) {
        if (account == null) return null;
        return new AccountReqDTO(account.getAccountNumber(),account.getBalance(), account.getAccountType(), account.getOwnerName());
    }



}

