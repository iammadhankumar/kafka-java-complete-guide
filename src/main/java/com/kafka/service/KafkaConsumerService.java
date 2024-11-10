package com.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for consuming messages from a Kafka topic.
 * This service listens to messages from the 'myTopic' Kafka topic, processes them,
 * and logs the appropriate information based on the content and structure of the message.
 *
 * The messages can be of various types such as String, Boolean, Number, JSON Array, or JSON Object.
 */
@Service
@Slf4j  // Lombok annotation to generate a logger
public class KafkaConsumerService {

    /**
     * This method listens to the Kafka topic 'myTopic' and processes the consumed messages.
     * It parses the message content and logs the message type (String, Boolean, Number, JSON Object, JSON Array).
     *
     * @param record The Kafka record containing the message consumed from the topic.
     */
    @KafkaListener(topics = "myTopic", groupId = "group_id")
    public void consumeMessage(ConsumerRecord<String, Object> record) {

        // Initialize Gson object to parse the message into JSON format.
        Gson gson = new Gson();
        
        // Convert the Kafka message to a JSON object for easier manipulation.
        JsonObject jsonObject = JsonParser.parseString(gson.toJson(record.value())).getAsJsonObject();
        
        // Extract the 'message' element from the JSON object.
        JsonElement messageElement = jsonObject.get("message");

        // Check if the 'message' element exists in the JSON object.
        if (messageElement != null) {
            // Determine the type of the 'message' element and handle accordingly.

            // If the 'message' element is a primitive (String, Boolean, Number).
            if (messageElement.isJsonPrimitive()) {

                JsonPrimitive primitive = messageElement.getAsJsonPrimitive();

                // Handle case where 'message' is a String.
                if (primitive.isString()) {
                    log.info("The consumed message is a String: " + primitive.getAsString());
                }
                // Handle case where 'message' is a Boolean.
                else if (primitive.isBoolean()) {
                    log.info("The consumed message is a Boolean: " + primitive.getAsBoolean());
                }
                // Handle case where 'message' is a Number.
                else if (primitive.isNumber()) {
                    log.info("The consumed message is a Number: " + primitive.getAsNumber());
                }
            }
            // If the 'message' element is a JSON array (list in Java).
            else if (messageElement.isJsonArray()) {

                JsonArray jsonArray = messageElement.getAsJsonArray();
                log.info("The consumed message is a JSON Array (List in Java). Size: " + jsonArray.size());
            }
            // If the 'message' element is a JSON object (map in Java).
            else if (messageElement.isJsonObject()) {

                JsonObject jsonObj = messageElement.getAsJsonObject();
                log.info("The consumed message is a JSON Object (Map in Java). Keys: " + jsonObj.keySet());
            }
        }
        // If the 'message' element is null or does not exist, log an error.
        else {
            log.error("The consumed message is null or does not exist.");
        }
    }
}
