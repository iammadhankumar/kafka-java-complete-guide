package com.kafka.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for sending messages to Kafka.
 *
 * This service encapsulates the logic of producing messages to a Kafka topic.
 * It uses KafkaTemplate to send messages to the topic asynchronously.
 */
@Service
@Slf4j
public class KafkaProducerService {

    // Kafka topic name for message production
    private static final String TOPIC = "myTopic";

    // KafkaTemplate to handle message sending
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends a message to the specified Kafka topic asynchronously.
     *
     * This method sends a message to the "myTopic" Kafka topic.
     * It is designed to handle any object type as the message content.
     *
     * @param message The message to be sent to Kafka. It can be any object type.
     * @return A CompletableFuture that can be used to check the result or handle further processing.
     */
    public CompletableFuture<SendResult<String, Object>> sendMessage(Object message) {
        // Send the message asynchronously using KafkaTemplate
        return kafkaTemplate.send(TOPIC, message);
              // Returns a CompletableFuture directly
    }

    /**
     * Example usage of the sendMessage method with handling success or failure.
     *
     * This is an example method that shows how you can handle the result
     * of the CompletableFuture returned by sendMessage.
     */
    public void sendMessageWithCallback(Object message) {
        sendMessage(message)
            .thenAccept(result -> {
                // Handle successful send result
                log.info("Message sent successfully to topic " + TOPIC + ": " + result.getProducerRecord().value());
            })
            .exceptionally(ex -> {
                // Handle failure (exceptions)
                log.error("Failed to send message to topic " + TOPIC + ": " + message);
                ex.printStackTrace();
                return null;  // Return null on failure
            });
    }
}
