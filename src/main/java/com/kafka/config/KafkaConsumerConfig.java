package com.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * Kafka consumer configuration class that sets up Kafka consumer properties and beans
 * for consuming messages from Kafka topics using the Spring Kafka framework.
 * 
 * This configuration enables Kafka listeners, sets up deserialization mechanisms for
 * both key and value, and defines the necessary Kafka consumer factory and listener
 * container factory.
 */
@EnableKafka  // Enable KafkaListener functionality in Spring
@Configuration  // Mark this class as a Spring configuration class
public class KafkaConsumerConfig {

    /**
     * Bean that creates a Kafka ConsumerFactory for creating Kafka consumer instances.
     * 
     * This factory is responsible for configuring how messages are consumed from Kafka, 
     * including the deserialization of keys and values.
     * 
     * @return a configured ConsumerFactory instance
     */
    @Bean
    ConsumerFactory<String, Object> consumerFactory() {
        // Configuration properties for the Kafka consumer
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka broker addresses
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id"); // Consumer group ID for this consumer
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Key deserialization class
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // Value deserialization class

        // Return the configured consumer factory with appropriate deserializers for key and value
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(Object.class));
    }

    /**
     * Bean that configures the Kafka listener container factory.
     * 
     * This factory is responsible for creating listener containers which manage 
     * message consumption and delivery from Kafka topics.
     * 
     * @return a configured ConcurrentKafkaListenerContainerFactory instance
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        // Create an instance of the Kafka listener container factory
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        
        // Set the consumer factory to be used by this listener container
        factory.setConsumerFactory(consumerFactory());

        // Return the configured factory
        return factory;
    }
}
