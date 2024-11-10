package com.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.service.KafkaProducerService;


/**
 * Controller responsible for handling Kafka message-related HTTP requests.
 * 
 * This controller exposes a POST endpoint to send messages to a Kafka topic
 * using a KafkaProducerService. The message is passed as a request body 
 * in JSON format and is forwarded to the Kafka producer for processing.
 * 
 * @author MadhanKumar
 */
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * Sends a message to a Kafka topic.
     * 
     * This endpoint accepts a POST request with a JSON message in the request body.
     * The message is forwarded to the KafkaProducerService for further processing
     * (e.g., publishing to a Kafka topic).
     *
     * @param message The message to be sent to Kafka, received in the request body.
     * @return A ResponseEntity indicating that the message has been processed successfully.
     */
    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody Object message) {
        // Delegate the message processing to KafkaProducerService
        kafkaProducerService.sendMessageWithCallback(message);
        
        // Return a success response to the client
        return ResponseEntity.ok("Message processed successfully.");
    }
}
