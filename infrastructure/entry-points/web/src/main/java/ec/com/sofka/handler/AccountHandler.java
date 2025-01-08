package ec.com.sofka.handler;

import ec.com.sofka.account.CreateAccountUseCase;
import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.validator.RequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {

    private final RequestValidator requestValidator;
    private final CreateAccountUseCase createAccountUseCase;

    public AccountHandler(
            RequestValidator requestValidator,
            CreateAccountUseCase createAccountUseCase
    ){
        this.requestValidator = requestValidator;
        this.createAccountUseCase = createAccountUseCase;
    }


    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .doOnNext(requestValidator::validate)
                .map(AccountMapper::toEntity)
                .flatMap(createAccountUseCase::execute)
                .map(AccountMapper::fromEntity)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO));
    }

}
