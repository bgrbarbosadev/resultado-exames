package br.com.bgrbarbosa.ms_customer.business.producer;

import br.com.bgrbarbosa.ms_customer.infraestruture.entity.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PublishedCustomerService {

    private final RabbitTemplate rabbitTemplate;

    private final String customerExchange = "exchange-customer-scheduling";

    private final String customerCreatedRoutingKey = "customer-create";

    private final String customerUpdateRoutingKey = "customer-update";

    private String customerDeletedRoutingKey = "customer-deleted";

    public void createCustomer(Customer entity) throws JsonProcessingException {
        var json = convertIntoJson(entity);
        rabbitTemplate.convertAndSend(customerExchange, customerCreatedRoutingKey, json);
    }

    public void updateCustomer(Customer entity) throws JsonProcessingException {
        var json = convertIntoJson(entity);
        rabbitTemplate.convertAndSend(customerExchange, customerUpdateRoutingKey, json);
    }

    public void deleteCustomer(UUID uuid) throws JsonProcessingException {
        var json = convertIntoJson(uuid);
        rabbitTemplate.convertAndSend(customerExchange, customerDeletedRoutingKey, json);
    }

    private String convertIntoJson(Customer entity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    private String convertIntoJson(UUID uuid) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(uuid);
    }
}
