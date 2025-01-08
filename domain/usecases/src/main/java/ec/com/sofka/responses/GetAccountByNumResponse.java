package ec.com.sofka.responses;

import ec.com.sofka.account.Account;
import ec.com.sofka.gateway.dto.AccountDTO;

public class GetAccountByNumResponse {
    private Account account;
    private AccountDTO accountDTO;

    public GetAccountByNumResponse(AccountDTO accountDTO, Account account) {
        this.accountDTO = accountDTO;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }
}
