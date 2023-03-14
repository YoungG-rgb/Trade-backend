package kg.tech.tradebackend.services;

public interface EmailSenderService {
    boolean send(Long userId, String subject, String body) throws Exception;
    boolean send(Long userId, String subject, String body, byte [] attachment, String fileName) throws Exception;
}
