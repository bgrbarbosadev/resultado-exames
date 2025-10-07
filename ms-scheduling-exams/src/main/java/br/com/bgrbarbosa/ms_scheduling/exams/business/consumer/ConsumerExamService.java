package br.com.bgrbarbosa.ms_scheduling.exams.business.consumer;


import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Exam;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository.ExamRepository;
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
public class ConsumerExamService {

    private final ObjectMapper mapper;

    private final ExamRepository examRepository;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-exam-create"),
            exchange = @Exchange(value = "exchange-exam-scheduling", type = ExchangeTypes.DIRECT),
            key = "exam-create"))
    public void createExam(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            Exam data = mapper.readValue(payload, Exam.class);
            examRepository.save(data);

        }catch (Exception e){
            log.error("Error receiving data from {}", e.getMessage());
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-exam-update"),
            exchange = @Exchange(value = "exchange-exam-scheduling", type = ExchangeTypes.DIRECT),
            key = "exam-update"))
    public void updateExam(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            Exam data = mapper.readValue(payload, Exam.class);
            examRepository.save(data);

        }catch (Exception e){
            log.error("Error receiving data from {}", e.getMessage());
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-exam-delete"),
            exchange = @Exchange(value = "exchange-exam-scheduling", type = ExchangeTypes.DIRECT),
            key = "exam-delete"))
    public void deleteExam(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            Exam data = mapper.readValue(payload, Exam.class);
            examRepository.deleteById(data.getCodExam());

        }catch (Exception e){
            log.error("Error receiving data from {}", e.getMessage());
        }
    }
}
