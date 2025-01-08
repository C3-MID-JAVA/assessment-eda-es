package ec.com.sofka.exceptions;

import ec.com.sofka.ErrorDetails;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Component
public class GlobalExceptionsHandler {


    public Mono<ServerResponse> handleException(Throwable ex) {
        if (ex instanceof ValidationException) {
            return createErrorResponse(ex, HttpStatus.BAD_REQUEST, "SOME FIELD(s) IN THE REQUEST HAS ERROR");
        } else if (ex instanceof RuntimeException) {
            return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "WE JUST GOT A NEW ERROR");
        }
        return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }

    private Mono<ServerResponse> createErrorResponse(Throwable ex, HttpStatus status, String message) {
        ErrorDetails errorResponse = new ErrorDetails(
                new Date(),
                message,
                List.of(ex.getMessage())
        );
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }


}
