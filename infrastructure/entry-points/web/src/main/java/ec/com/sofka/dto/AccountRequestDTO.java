package ec.com.sofka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class AccountRequestDTO {

    @NotBlank(message = "UserId cannot be blank")
    private String userId;

    @NotBlank(message = "AccountNumber cannot be blank")
    private String accountNumber;

    @NotNull(message = "Balance is mandatory")
    private BigDecimal balance;

    public AccountRequestDTO() {
    }

    public AccountRequestDTO(String userId,
                             String accountNumber,
                             BigDecimal balance
    ) {

        this.balance = balance;
        this.userId = userId;
        this.accountNumber = accountNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
