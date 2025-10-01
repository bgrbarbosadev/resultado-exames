package br.com.bgrbarbosa.ms_exame.business.producer;

import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PublishedExamService {

    private final RabbitTemplate rabbitTemplate;

    private final String examExchange = "exchange-exam-scheduling";

    private final String examCreatedRoutingKey = "exam-create";

    private final String examUpdateRoutingKey = "exam-update";

    private String examDeletedRoutingKey = "exam-deleted";

    public void createExam(Exam entity) throws JsonProcessingException {
        var json = convertIntoJson(entity);
        rabbitTemplate.convertAndSend(examExchange, examCreatedRoutingKey, json);
    }

    public void updateExam(Exam entity) throws JsonProcessingException {
        var json = convertIntoJson(entity);
        rabbitTemplate.convertAndSend(examExchange, examUpdateRoutingKey, json);
    }

    public void deleteExam(UUID uuid) throws JsonProcessingException {
        var json = convertIntoJson(uuid);
        rabbitTemplate.convertAndSend(examExchange, examDeletedRoutingKey, json);
    }

    private String convertIntoJson(Exam entity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    private String convertIntoJson(UUID uuid) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(uuid);
    }
}
