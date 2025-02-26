package br.muralis.challenge.application;

import br.muralis.challenge.application.component.CheckPressureAnomalyComponent;
import br.muralis.challenge.domain.dto.RequestPipePressureDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class ConsumerPipePressureService {

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    private final Queue queueNotification;

    private final CheckPressureAnomalyComponent checkPressureAnomalyComponent;

    public ConsumerPipePressureService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, @Qualifier("pipe_pressure_persist") Queue queue,
                                       @Qualifier("notifications") Queue queueNotification, CheckPressureAnomalyComponent checkPressureAnomalyComponent) {
        super();
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.queueNotification = queueNotification;
        this.checkPressureAnomalyComponent = checkPressureAnomalyComponent;
    }

    @Bean
    public Consumer<String> pipePressure() {
        return requestString -> {
            try {

                //Convert the message to a object
                var request = objectMapper.readValue(requestString, RequestPipePressureDTO.class);

                //process the message
                log.info("Consumer and processed the message {}", request);

                //publish to persist
                rabbitTemplate.convertAndSend(this.queue.getName(), requestString);

                //Check for anomaly
                var notification = checkPressureAnomalyComponent.check(request);

                if (notification != null) {
                    rabbitTemplate.convertAndSend(this.queueNotification.getName(), this.objectMapper.writeValueAsString(notification));
                }

            } catch (JsonProcessingException e) {
                log.error("Error on consume pipe_pressure message!", e);
                throw new RuntimeException("Error on consume pipe_pressure message!", e);
            }
        };
    }

}
