package ec.com.sofka.router;

import ec.com.sofka.ErrorResponse;
import ec.com.sofka.data.AccountReqByIdDTO;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.globalexceptions.GlobalErrorHandler;
import ec.com.sofka.handler.AccountHandler;
import ec.com.sofka.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AccountRouter {

    private final AccountHandler handler;
    private final ValidationService validationService;
    private final GlobalErrorHandler globalErrorHandler;

    public AccountRouter(AccountHandler handler, ValidationService validationService, GlobalErrorHandler globalErrorHandler) {
        this.handler = handler;
        this.validationService = validationService;
        this.globalErrorHandler = globalErrorHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "create",
                            summary = "Create a new account",
                            description = "This endpoint allows the creation of a new bank account for a user. It accepts user details in the request body and returns the created account's information.",
                            requestBody = @RequestBody(
                                    description = "Account creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Account successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/update",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "update",
                            summary = "Create a new account",
                            description = "This endpoint allows the creation of a new bank account for a user. It accepts user details in the request body and returns the created account's information.",
                            requestBody = @RequestBody(
                                    description = "Account creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Account successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/accountId",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getAccountById",
                            summary = "Create a new account",
                            description = "This endpoint allows the creation of a new bank account for a user. It accepts user details in the request body and returns the created account's information.",
                            requestBody = @RequestBody(
                                    description = "Account creation details",
                                    required = false,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountReqByIdDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Account successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/getAll",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "listAccounts",
                            summary = "List all accounts",
                            description = "Fetches a list of all accounts available in the system.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved the list of accounts",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO[].class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/accountNumber",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getAccountByAccountNumber",
                            summary = "Create a new account",
                            description = "This endpoint allows the creation of a new bank account for a user. It accepts user details in the request body and returns the created account's information.",
                            requestBody = @RequestBody(
                                    description = "Account creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountReqByIdDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Account successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/{accountId}/balance",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getAccountBalance",
                            summary = "Get account balance",
                            description = "Fetches the balance of the account associated with the given account ID.",
                            parameters = {
                                    @Parameter(
                                            name = "accountId",
                                            description = "The account ID to retrieve balance info",
                                            required = true,
                                            in = ParameterIn.PATH
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved the account balance",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> accountRoutes() {
        return RouterFunctions
                .route(POST("/accounts").and(accept(MediaType.APPLICATION_JSON)), this::createAccount)
                .andRoute(POST("/update").and(accept(MediaType.APPLICATION_JSON)), this::updateAccount)
                .andRoute(POST("/accounts/accountNumber").and(accept(MediaType.APPLICATION_JSON)), this::getAccountByAccountNumber)
                .andRoute(GET("/accounts/getAll"), this::listAccounts)
                //.andRoute(POST("/accounts/accountId").and(accept(MediaType.APPLICATION_JSON)), this::getAccountById)
        ;
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(dto -> validationService.validate(dto, AccountRequestDTO.class))
                .flatMap(handler::createAccount)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }

    public Mono<ServerResponse> updateAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(dto -> validationService.validate(dto, AccountRequestDTO.class))
                .flatMap(handler::updateAccount)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }


    public Mono<ServerResponse> getAccountByAccountNumber(ServerRequest request) {
        return request.bodyToMono(AccountReqByIdDTO.class)
                .doOnNext(dto -> {
                })
                .flatMap(handler::getAccountByNumber)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }


    public Mono<ServerResponse> getAccountById(ServerRequest request) {
        return request.bodyToMono(AccountReqByIdDTO.class)
                .doOnNext(dto -> {
                })
                .flatMap(handler::getAccountById)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }


    public Mono<ServerResponse> listAccounts(ServerRequest request) {
        return handler.getAllAccounts()
                .collectList()
                .flatMap(accounts -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accounts))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }


}
