package ec.com.sofka.exception;

/** Exception for handling bad request scenarios. */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
