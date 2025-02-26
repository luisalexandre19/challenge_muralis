package br.muralis.challenge.application;

import br.muralis.challenge.domain.dto.RequestHydrantReadingDTO;
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
public class ConsumerHydrantReadingsService {

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    public ConsumerHydrantReadingsService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, @Qualifier("hydrant_readings_persist") Queue queue) {
        super();
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @Bean
    public Consumer<String> hydrantReadings() {
        return requestString -> {
            try {

                //Convert the message to a object
                var request = objectMapper.readValue(requestString, RequestHydrantReadingDTO.class);

                //process the message
                log.info("Consumer and processed the message {}", request);

                //publish to persist
                rabbitTemplate.convertAndSend(this.queue.getName(), requestString);

            } catch (JsonProcessingException e) {
                log.error("Error on consume hydrant_readings message!", e);
                throw new RuntimeException("Error on consume hydrant_readings message!", e);
            }
        };
    }

}
