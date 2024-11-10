package backend.bd_proyect.Exception;

public class InvalidBookParameterException extends RuntimeException {
    public InvalidBookParameterException(String message) {
        super(message);
    }
}
