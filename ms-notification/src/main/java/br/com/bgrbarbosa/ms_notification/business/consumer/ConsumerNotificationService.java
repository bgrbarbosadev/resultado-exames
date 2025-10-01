package br.com.bgrbarbosa.ms_notification.business.consumer;

import br.com.bgrbarbosa.ms_notification.api.dto.converter.NotificationMapper;
import br.com.bgrbarbosa.ms_notification.api.dto.request.NotificationRequestDTO;
import br.com.bgrbarbosa.ms_notification.business.MailService;
import br.com.bgrbarbosa.ms_notification.business.NotificationService;
import br.com.bgrbarbosa.ms_notification.infraestruture.entity.Notification;
import br.com.bgrbarbosa.ms_notification.infraestruture.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumerNotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper notificationMapper;
    private final MailService mailService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-notification"),
            exchange = @Exchange(value = "exchange-exam-scheduling", type = ExchangeTypes.DIRECT),
            key = "notification"))
    public void createNotification(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            NotificationRequestDTO data = mapper.readValue(payload, NotificationRequestDTO.class);
            Notification aux = notificationMapper.parseToResponseEntity(data);
            repository.save(aux);
            mailService.sendMail(notificationMapper.parseToResponseDto(aux));

        }catch (Exception e){
            log.error("Error receiving data from {}", e.getMessage());
        }
    }

}
