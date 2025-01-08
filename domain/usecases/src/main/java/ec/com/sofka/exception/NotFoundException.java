package ec.com.sofka.exception;

/** Exception for handling not found scenarios. */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
