package ec.com.sofka.handler;

import ec.com.sofka.CreateAccountUseCase;
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
    /*
        private final GetAccountByNumberUseCase getAccountByNumberUseCase;
    */
    private final CreateAccountUseCase createAccountUseCase;
    /*private final GetAllByUserIdUseCase getAllByUserIdUseCase;*/

    public AccountHandler(RequestValidator requestValidator, CreateAccountUseCase createAccountUseCase) {
        this.requestValidator = requestValidator;
        this.createAccountUseCase = createAccountUseCase;
    }

    /* public AccountHandler(
            RequestValidator requestValidator,
            GetAccountByNumberUseCase getAccountByNumberUseCase,
            CreateAccountUseCase createAccountUseCase,
            GetAllByUserIdUseCase getAllByUserIdUseCase
    ) {
        this.requestValidator = requestValidator;
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.getAllByUserIdUseCase = getAllByUserIdUseCase;
    }
*/
    /*public Mono<ServerResponse> getByAccountNumber(ServerRequest request){
        String accountNumber = request.pathVariable("id");

        return getAccountByNumberUseCase.apply(accountNumber)
                .map(AccountMapper::fromEntity)
                .flatMap(accountResponseDTO ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(accountResponseDTO));
    }*/

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

   /* public Mono<ServerResponse> getAllByUserId(ServerRequest request){
        String userId = request.pathVariable("userId");

        return getAllByUserIdUseCase.apply(userId)
                .map(AccountMapper::fromEntity)
                .collectList()
                .flatMap(accountResponseDTOs ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(accountResponseDTOs));
    }*/
}
