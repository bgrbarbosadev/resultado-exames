package br.com.bgrbarbosa.ms_scheduling.exams.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange createDirectExchangeCustomerScheduling(){
        return ExchangeBuilder.directExchange("exchange-customer-scheduling").build();
    }

    @Bean
    public DirectExchange createDirectExchangeExamScheduling(){
        return ExchangeBuilder.directExchange("exchange-exam-scheduling").build();
    }

    @Bean
    public DirectExchange createDirectExchangeExamResult(){
        return ExchangeBuilder.directExchange("exchange-exam-result").build();
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
