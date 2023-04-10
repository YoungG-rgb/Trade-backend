package kg.tech.tradebackend.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MailSenderConfig {
    private final MailProperty mailProperties;

    @Bean
    public MimeMessage mimeMessage() {
        return new MimeMessage(session());
    }

    @Bean
    public Session session() {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailProperties.getHost());
        props.put("mail.from", mailProperties.getUsername());
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", mailProperties.getPort());

        return Session.getInstance(props, null);
    }

    @Bean
    public Transport transport() throws MessagingException {
        Transport transport = session().getTransport();
        try {
            transport.connect(mailProperties.getUsername(), mailProperties.getPassword());
        } catch (Exception exception) {
            log.error("Не удалось подключиться к gmail.com");
        }
        return transport;
    }
}
