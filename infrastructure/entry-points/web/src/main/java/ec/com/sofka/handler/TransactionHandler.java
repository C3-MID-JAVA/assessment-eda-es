package ec.com.sofka.handler;

import ec.com.sofka.dto.TransactionRequestDTO;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.transaction.CreateTransactionUseCase;
import ec.com.sofka.validator.RequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class TransactionHandler {

    private final RequestValidator requestValidator;
    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionHandler(RequestValidator requestValidator, CreateTransactionUseCase createTransactionUseCase) {
        this.requestValidator = requestValidator;
        this.createTransactionUseCase = createTransactionUseCase;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(TransactionRequestDTO.class)
                .doOnNext(requestValidator::validate)
                .map(TransactionMapper::toEntity)
                .flatMap(createTransactionUseCase::execute)
                .map(TransactionMapper::fromEntity)
                .flatMap(transactionResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionResponseDTO));
    }

}
