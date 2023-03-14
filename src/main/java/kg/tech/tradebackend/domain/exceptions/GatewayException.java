package kg.tech.tradebackend.domain.exceptions;

public class GatewayException extends Exception {
    public GatewayException(String message) {
        super(message);
    }
}