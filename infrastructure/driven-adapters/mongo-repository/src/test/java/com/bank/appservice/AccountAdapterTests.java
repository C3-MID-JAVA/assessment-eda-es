package com.bank.appservice;

import com.bank.account.Account;
import com.bank.TestConfiguration;
import com.bank.account.values.AccountId;
import com.bank.account.values.objects.AccountNumber;
import com.bank.account.values.objects.Balance;
import com.bank.account.values.objects.Holder;
import com.bank.account.values.objects.IsActive;
import com.bank.gateway.IAccountRepository;
import com.bank.gateway.dto.mapper.AccountDTOMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class)
@DataMongoTest
public class AccountAdapterTests {
    private IAccountRepository accountRepository;


    @Autowired
    public AccountAdapterTests(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Test
    public void SaveAndFindByIdOK() {
        String id = UUID.randomUUID().toString();
        Account account = new Account(
                AccountId.of(id),
                AccountNumber.of("12345679"),
                Holder.of("test holder"),
                Balance.of(BigDecimal.valueOf(8503)),
                IsActive.of(true)
        );

        Mono<Account> save = accountRepository.createAccount(AccountDTOMapper.toDTO(account));

        StepVerifier.create(save)
                .assertNext(savedAccount -> {
                    assertThat(savedAccount.getId()).isNotNull();
                    assertThat(savedAccount.getId()).isEqualTo(id);
                })
                .verifyComplete();

        Mono<Account> find = accountRepository.findAccountById(account.getId().getValue()).map(AccountDTOMapper::toModel);

        StepVerifier.create(find)
                .assertNext(foundAccount -> {
                    assertThat(foundAccount.getId()).isEqualTo(account.getId());
                    assertThat(foundAccount.getBalance().getValue()).isEqualByComparingTo(new BigDecimal(8503));
                })
                .verifyComplete();
    }
}
