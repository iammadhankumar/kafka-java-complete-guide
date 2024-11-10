package com.kafka.config;

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

/**
 * Kafka producer configuration class that sets up Kafka producer properties and beans
 * for sending messages to Kafka topics using the Spring Kafka framework.
 * 
 * This configuration includes the setup of producer properties, the producer factory, 
 * and the KafkaTemplate to enable the sending of messages.
 */
@Configuration  // Mark this class as a Spring configuration class
public class KafkaProducerConfig {

    /**
     * Bean that creates a Kafka ProducerFactory for producing messages to Kafka topics.
     * 
     * This factory configures the producer with necessary properties, including 
     * the serialization mechanisms for both the key and the value of messages.
     * 
     * @return a configured ProducerFactory instance
     */
    @Bean
    ProducerFactory<String, Object> producerFactory() {
        // Configuration properties for the Kafka producer
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka broker addresses
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Key serialization class
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Value serialization class

        // Return the configured producer factory with appropriate serializers for key and value
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Bean that creates and configures a KafkaTemplate for sending messages to Kafka topics.
     * 
     * The KafkaTemplate simplifies interaction with Kafka by providing methods to send
     * messages to a Kafka topic with ease, using the producer factory for the underlying
     * producer configuration.
     * 
     * @return a configured KafkaTemplate instance
     */
    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        // Return a KafkaTemplate instance configured with the producer factory
        return new KafkaTemplate<>(producerFactory());
    }
}
