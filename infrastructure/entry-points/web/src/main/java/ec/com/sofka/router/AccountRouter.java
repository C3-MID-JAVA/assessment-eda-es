package ec.com.sofka.router;

import ec.com.sofka.ErrorResponse;
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
                    path = "/accounts/accountNumber/{accountNumber}",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getByAccountNumber",
                            summary = "Get account by account number",
                            description = "Fetches the account details associated with the given account number.",
                            parameters = {
                                    @Parameter(
                                            name = "accountNumber",
                                            description = "The account number to retrieve account info",
                                            required = true,
                                            in = ParameterIn.PATH
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved the account details",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found.",
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
                    path = "/accounts/{accountId}",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getAccountById",
                            summary = "Get account by ID",
                            description = "Fetches the account details associated with the given account ID.",
                            parameters = {
                                    @Parameter(
                                            name = "accountId",
                                            description = "The account ID to retrieve account info",
                                            required = true,
                                            in = ParameterIn.PATH
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved the account details",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found.",
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
                //.andRoute(GET("/accounts/accountNumber/{accountNumber}"), this::getAccountByAccountNumber)
                .andRoute(GET("/accounts/getAll"), this::listAccounts)
               // .andRoute(GET("/accounts/{accountId}"), this::getAccountById)
               // .andRoute(GET("/accounts/{accountId}/balance"), this::getAccountBalance);
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
/*
    public Mono<ServerResponse> getAccountByAccountNumber(ServerRequest request) {
        String accountNumber = request.pathVariable("accountNumber");

        // Construir el objeto AccountRequestDTO
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(accountNumber);

        return handler.getAccountByNumber(accountRequestDTO)
                .flatMap(accountResponseDTO -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }
*/


    public Mono<ServerResponse> listAccounts(ServerRequest request) {
        return handler.getAllAccounts()
                .collectList()
                .flatMap(accounts -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accounts))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }
/*
    private Mono<ServerResponse> getAccountById(ServerRequest request) {
        String accountId = request.pathVariable("accountId");
        return handler.getAccountByAccountId(accountId)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }*/
/*
    private Mono<ServerResponse> getAccountBalance(ServerRequest request) {
        String accountId = request.pathVariable("accountId");
        return handler.getCheckBalance(accountId)
                .flatMap(balance -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(balance))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }*/

}
