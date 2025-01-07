package ec.com.sofka.router;

import ec.com.sofka.dto.TransactionRequestDTO;
import ec.com.sofka.dto.TransactionResponseDTO;
import ec.com.sofka.exception.ApiError;
import ec.com.sofka.handler.TransactionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class TransactionRouter {

    private final TransactionHandler transactionHandler;

    public TransactionRouter(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/transactions",
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "create",
                            summary = "Create a new transaction",
                            description = "This endpoint allows you to create a new transaction. It calculates fees and updates the account balance based on the transaction type.",
                            requestBody = @RequestBody(
                                    description = "Transaction creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TransactionRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Transaction successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, invalid transaction data (e.g., invalid amount, missing account number)",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found, the provided account number does not exist",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Insufficient balance for this transaction",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                                    )
                            }
                    )
            )
    })


    public RouterFunction<ServerResponse> transactionRoutes() {
        return RouterFunctions
                .route(RequestPredicates.POST("/transactions").and(accept(MediaType.APPLICATION_JSON)), transactionHandler::create);
    }


}
