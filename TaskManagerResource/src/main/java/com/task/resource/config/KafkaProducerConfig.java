package com.task.resource.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.task.library.kafka.KafkaTaskMessage;

@Configuration
public class KafkaProducerConfig {
    
    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producuerConfig());
    }

    @Bean
    Map<String, Object> producuerConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "kafkaTaskMessage:com.task.library.kafka.KafkaTaskMessage");
        config.put(JsonSerializer.TYPE_MAPPINGS, "kafkaListMessage:com.task.library.kafka.KafkaListMessage");

        return config;
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
