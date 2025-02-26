package br.muralis.challenge.infra.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Bean("hydrant_readings_persist")
    Queue queue_hydrant_readings_persist() {
        return QueueBuilder.durable("hydrant_readings_persist")
                .stream()
                .build();
    }

    @Bean("pipe_pressure_persist")
    Queue queue_pipe_pressure_persist() {
        return QueueBuilder.durable("pipe_pressure_persist")
                .stream()
                .build();
    }

    @Bean("notifications")
    Queue queue_notifications() {
        return QueueBuilder.durable("notifications")
                .stream()
                .build();
    }

}
