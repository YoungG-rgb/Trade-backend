package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.configs.MailProperty;
import kg.tech.tradebackend.repositories.UserRepository;
import kg.tech.tradebackend.services.EmailSenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;

import static kg.tech.tradebackend.utils.BaseValidator.isValidEmail;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailSenderService {
    MimeMessage mimeMessage;
    Transport transport;
    MailProperty mailProperties;
    UserRepository userRepository;

    @Override
    public boolean send(Long userId, String subject, String body) throws Exception {
        return send(userId, subject, body, null, null);
    }

    @Override
    public boolean send(Long userId, String subject, String body, byte[] attachment, String fileName) throws Exception {
        String email = userRepository.findById(userId).get().getEmail();
        isValidEmail(email);

        if (attachment != null) {
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachment, mailProperties.getFileType())));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            mimeMessage.setContent(multipart);
        }

        try (transport) {
            mimeMessage.setRecipients(Message.RecipientType.TO, email);
            mimeMessage.setSubject(subject);
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText(body);

            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        }

        log.info("send data to email: " + email);
        return true;
    }
}
