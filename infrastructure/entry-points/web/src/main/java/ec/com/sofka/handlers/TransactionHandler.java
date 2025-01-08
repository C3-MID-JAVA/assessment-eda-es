package ec.com.sofka.handlers;
import ec.com.sofka.CreateTransactionUseCase;
import ec.com.sofka.GetAccountByNumberUseCase;
import ec.com.sofka.data.transaction.TransactionRequestDTO;
import ec.com.sofka.data.transaction.TransactionResponseDTO;
import ec.com.sofka.enums.TypeTransaction;
import ec.com.sofka.exceptions.CuentaNoEncontradaException;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.request.account.GetAccountRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class TransactionHandler {
    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetAccountByNumberUseCase getAccountByNumberUseCase;


    public TransactionHandler(CreateTransactionUseCase createTransactionUseCase, GetAccountByNumberUseCase getAccountByNumberUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
    }

    public Mono<TransactionResponseDTO> createTransaction(TransactionRequestDTO transactionRequestDTO){
        BigDecimal costo = obtenerCostoTipoTransaccion(transactionRequestDTO.getType());
        boolean esRetiro = esRetiro(transactionRequestDTO.getType());
        GetAccountRequest requestAcc= new GetAccountRequest(transactionRequestDTO.getOperationId(),transactionRequestDTO.getIdAccount());

        return validarAccount(requestAcc)
                .flatMap(account -> createTransactionUseCase.validarTransaction2(account, transactionRequestDTO.getAmount(), transactionRequestDTO.getType(), costo, esRetiro))
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getOperationId(),
                        transaction.getTransactionId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getCost(),
                        transaction.getIdAccount(),
                        transaction.getStatus()
                ));

    }


    private Mono<AccountDTO> validarAccount( GetAccountRequest requestAcc) {
        return getAccountByNumberUseCase.execute(requestAcc)
                .switchIfEmpty(Mono.error(new CuentaNoEncontradaException("Cuenta no encontrada")))
                .map(accountResponseDTO -> {

                    //System.out.println("Cuenta validada"+account.getId() +"  " + account.getBalance()+"  "+account.getOwner()+"  "+account.getAccountNumber());
                    // Map other fields as necessary
                    return new AccountDTO(
                            accountResponseDTO.getAccountId(),
                    accountResponseDTO.getBalance(),
                    accountResponseDTO.getName(),
                    accountResponseDTO.getAccountNumber(),
                    accountResponseDTO.getStatus());
                });

    }
    private BigDecimal obtenerCostoTipoTransaccion(String tipo) {
        if (!TypeTransaction.validadorTipo.validar(tipo)) {
            throw new IllegalArgumentException("Tipo de transacción no válido: " + tipo);
        }
        return TypeTransaction.fromString(tipo).getCosto();
    }

    private boolean esRetiro(String tipo) {
        return tipo.startsWith("RETIRO") || tipo.equals("COMPRA_WEB") || tipo.equals("COMPRA_ESTABLECIMIENTO");
    }

}
