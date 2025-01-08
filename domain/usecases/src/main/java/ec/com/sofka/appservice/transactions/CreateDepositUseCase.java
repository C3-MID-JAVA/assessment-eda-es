package ec.com.sofka.appservice.transactions;

import ec.com.sofka.appservice.data.request.CreateTransactionRequest;
import ec.com.sofka.appservice.data.response.TransactionResponse;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.transaction.Transaction;
import ec.com.sofka.appservice.gateway.IBusMessage;
import ec.com.sofka.appservice.transactions.transactionprocess.ProcessTransactionUseCase;
import ec.com.sofka.enums.OperationType;
import reactor.core.publisher.Mono;

public class CreateDepositUseCase implements IUseCase<CreateTransactionRequest, TransactionResponse> {

    private final ProcessTransactionUseCase processTransactionUseCase;
    private final IBusMessage busMessage;

    public CreateDepositUseCase(ProcessTransactionUseCase processTransactionUseCase, IBusMessage busMessage) {
        this.processTransactionUseCase = processTransactionUseCase;
        this.busMessage = busMessage;
    }

    @Override
    public Mono<TransactionResponse> execute(CreateTransactionRequest transaction) {
        return processTransactionUseCase.apply(transaction, OperationType.DEPOSIT)
                .doOnSuccess(savedTransaction -> busMessage.sendMsg("transaction", "Transaction type: deposit " + savedTransaction.toString()));
    }

}
