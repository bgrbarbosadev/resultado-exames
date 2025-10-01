package br.com.bgrbarbosa.ms_scheduling.exams.business.consumer;


import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Customer;
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

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "queue-customer-deleted"),
//            exchange = @Exchange(value = "exchange-customer-scheduling"),
//            key = "customer-deleted" // Routing key
//    ))
//    public void deleteCustomer(@Payload String idString){
//        String cleanIdString = idString.replace("\"", "");
//        UUID id = UUID.fromString(cleanIdString);
//        try {
//            log.info("Attempting to delete customer with UUID: {}", id);
//            customerRepository.deleteById(id);
//            log.info("Customer with UUID {} deleted successfully.", id);
//        }catch (Exception e){
//            log.error("Error deleting customer with UUID {}: {}", id, e.getMessage());
//        }
//    }
}
