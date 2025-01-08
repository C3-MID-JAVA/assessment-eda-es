package com.bank.appservice;

import com.bank.account.Account;
import com.bank.account.values.AccountId;
import com.bank.account.values.objects.AccountNumber;
import com.bank.account.values.objects.Balance;
import com.bank.account.values.objects.Holder;
import com.bank.account.values.objects.IsActive;
import com.bank.gateway.dto.mapper.OperationDTOMapper;
import com.bank.operation.Operation;
import com.bank.operation.values.OperationId;
import com.bank.operation.values.objects.OperationTypesEnum;
import com.bank.TestConfiguration;
import com.bank.gateway.IOperationRepository;
import com.bank.operation.values.objects.Type;
import com.bank.operation.values.objects.Value;
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
public class OperationAdapterTests {
    private IOperationRepository operationRepository;


    @Autowired
    public OperationAdapterTests(IOperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Test
    public void SaveAndFindByIdOK() {
        String accId = UUID.randomUUID().toString();
        Account account = new Account(
                AccountId.of(accId),
                AccountNumber.of("12345679"),
                Holder.of("test holder"),
                Balance.of(BigDecimal.valueOf(8503)),
                IsActive.of(true)
        );

        String opId = UUID.randomUUID().toString();
        Operation operation = new Operation(
                OperationId.of(opId),
                Value.of(BigDecimal.valueOf(500)),
                Type.of(OperationTypesEnum.ACCOUNT_DEPOSIT),
                account
        );

        Mono<Operation> save = operationRepository.createOperation(OperationDTOMapper.toDTO(operation));

        StepVerifier.create(save)
                .assertNext(savedOperation -> {
                    assertThat(savedOperation.getId()).isNotNull();
                    assertThat(savedOperation.getId()).isEqualTo(opId);
                })
                .verifyComplete();

        Mono<Operation> find = operationRepository.findOperationById(operation.getId().getValue());

        StepVerifier.create(find)
                .assertNext(foundOperation -> {
                    assertThat(foundOperation.getId()).isEqualTo(operation.getId());
                    assertThat(foundOperation.getValue().getValue()).isEqualByComparingTo(new BigDecimal(500));
                })
                .verifyComplete();
    }
}
