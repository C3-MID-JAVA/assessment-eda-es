package com.bank.aggregate;

import com.bank.account.Account;
import com.bank.account.values.AccountId;
import com.bank.account.values.objects.Balance;
import com.bank.account.values.objects.Holder;
import com.bank.account.values.objects.IsActive;
import com.bank.account.values.objects.AccountNumber;
import com.bank.aggregate.events.AccountCreated;
import com.bank.aggregate.events.AccountUpdated;
import com.bank.aggregate.events.OperationCreated;
import com.bank.generics.domain.DomainActionsContainer;
import com.bank.operation.Operation;
import com.bank.operation.values.OperationId;
import com.bank.operation.values.objects.Type;
import com.bank.operation.values.objects.Value;

public class CustomerHandler extends DomainActionsContainer {
    public CustomerHandler(Customer customer) {
        addDomainActions((AccountCreated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    AccountNumber.of(event.getAccountNumber()),
                    Holder.of(event.getAccountHolder()),
                    Balance.of(event.getAccountBalance()),
                    IsActive.of(event.getAccountIsActive())
            );
            customer.setAccount(account);
        });

        addDomainActions((AccountUpdated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    AccountNumber.of(event.getAccountNumber()),
                    Holder.of(event.getAccountHolder()),
                    Balance.of(event.getAccountBalance()),
                    IsActive.of(event.getAccountIsActive())
            );
            customer.setAccount(account);
        });

        addDomainActions((OperationCreated event) -> {
            Operation operation = new Operation(
                    OperationId.of(event.getOperationId()),
                    Value.of(event.getOperationValue()),
                    Type.of(event.getOperationType()),
                    event.getOperationAccount()
            );
            customer.setOperation(operation);
        });
    }
}
