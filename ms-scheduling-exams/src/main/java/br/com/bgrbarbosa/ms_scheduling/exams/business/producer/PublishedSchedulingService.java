package br.com.bgrbarbosa.ms_scheduling.exams.business.producer;

import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response.NotificationDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Scheduling;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PublishedSchedulingService {

    private final RabbitTemplate rabbitTemplate;

    private final String schedulingExchange = "exchange-exam-scheduling";

    private final String schedulingCreatedRoutingKey = "notification";

    public void createUpdateScheduling(NotificationDTO entity) throws JsonProcessingException {
        //var json = convertIntoJson(entity);
        rabbitTemplate.convertAndSend(schedulingExchange, schedulingCreatedRoutingKey, entity);
    }

    private String convertIntoJson(NotificationDTO entity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(entity);
        return json;
    }
}
