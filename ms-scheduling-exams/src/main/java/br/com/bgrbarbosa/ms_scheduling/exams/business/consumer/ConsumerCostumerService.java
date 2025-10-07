package br.com.bgrbarbosa.ms_scheduling.exams.business.consumer;


import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Customer;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Exam;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository.CustomerRepository;
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
public class ConsumerCostumerService {

    private final ObjectMapper mapper;

    private final CustomerRepository customerRepository;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-customer-create"),
            exchange = @Exchange(value = "exchange-customer-scheduling", type = ExchangeTypes.DIRECT),
            key = "customer-create"))
    public void createCustomer(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            Customer data = mapper.readValue(payload, Customer.class);
            customerRepository.save(data);

        }catch (Exception e){
            log.error("Error receiving data from {}", e.getMessage());
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-customer-update"),
            exchange = @Exchange(value = "exchange-customer-scheduling", type = ExchangeTypes.DIRECT),
            key = "customer-update"))
    public void updateCustomer(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            Customer data = mapper.readValue(payload, Customer.class);
            customerRepository.save(data);

        }catch (Exception e){
            log.error("Error receiving data from {}", e.getMessage());
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-customer-delete"),
            exchange = @Exchange(value = "exchange-customer-scheduling", type = ExchangeTypes.DIRECT),
            key = "customer-delete"))
    public void deleteCustomer(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            Customer data = mapper.readValue(payload, Customer.class);
            customerRepository.deleteById(data.getCodCustomer());

        }catch (Exception e){
            log.error("Error receiving data from {}", e.getMessage());
        }
    }
}
