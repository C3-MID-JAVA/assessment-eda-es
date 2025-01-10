package ec.com.sofka.router;

import ec.com.sofka.data.UserRequestDTO;
import ec.com.sofka.data.UserResponseDTO;
import ec.com.sofka.exception.RequestValidator;
import ec.com.sofka.exception.model.ErrorDetails;
import ec.com.sofka.handlers.user.CreateUserHandler;
import ec.com.sofka.handlers.user.GetAllUsersHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {
    private final RequestValidator requestValidator;
    private final GetAllUsersHandler getAllUsersHandler;
    private final CreateUserHandler createUserHandler;

    public UserRouter(RequestValidator requestValidator, GetAllUsersHandler handler, CreateUserHandler createUserHandler) {
        this.requestValidator = requestValidator;
        this.getAllUsersHandler = handler;
        this.createUserHandler = createUserHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/users",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CreateUserHandler.class,
                    beanMethod = "save",
                    operation = @Operation(
                            tags = {"Users"},
                            operationId = "createUser",
                            summary = "Create a new user",
                            description = "Create a new user from the request data.",
                            requestBody = @RequestBody(
                                    description = "Details of the required entity.",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Record created successfully.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "422",
                                            description = "The entity has a conflict.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal application problems.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/users",
                    method = RequestMethod.GET,
                    beanClass = GetAllUsersHandler.class,
                    beanMethod = "getAllUsers",
                    operation = @Operation(
                            tags = {"Users"},
                            operationId = "getAllUsers",
                            summary = "Get all users",
                            description = "Get all registered users.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully obtained all registered users.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal application problems.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> usersRouters() {
        return RouterFunctions
                .route(POST("/api/users").and(accept(APPLICATION_JSON)), this::saveUser)
                .andRoute(GET("/api/users"), this::allUsers);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .flatMap(requestValidator::validate)
                .flatMap(createUserHandler::save)
                .flatMap(response ->
                        ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(response));

    }

    public Mono<ServerResponse> allUsers(ServerRequest request) {
        return getAllUsersHandler.getAllUsers()
                .collectList()
                .flatMap(list -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(list)
                );
    }

}