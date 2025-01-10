package ec.com.sofka.exception;

public class TransactionRejectedException  extends RuntimeException {
    public TransactionRejectedException(String message) {
        super(message);
    }
}