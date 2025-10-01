package br.com.bgrbarbosa.ms_notification.business.impl;

import br.com.bgrbarbosa.ms_notification.api.dto.response.NotificationResponseDTO;
import br.com.bgrbarbosa.ms_notification.business.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sourceEmail;

    @Override
    public void sendMail(NotificationResponseDTO dto) {

        String to = dto.emailRecipient();
        String subject = "Exam result";
        String message = dto.message();

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(sourceEmail);
        mensagem.setTo(to);
        mensagem.setSubject(subject);
        mensagem.setText(message);
        mailSender.send(mensagem);
    }
}
