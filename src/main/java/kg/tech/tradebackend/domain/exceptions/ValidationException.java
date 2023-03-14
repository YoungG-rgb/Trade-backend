package kg.tech.tradebackend.domain.exceptions;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
