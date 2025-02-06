package com.courses.kafkacurse.emailnotificationservice.handler;

import com.courses.kafkacurse.corekafkalibrary.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
// This annotation can be used in classes or method intended to work as target to new coming Kafka messages
// For multiple topics -> (topics={"topic1", "topic2"})
@KafkaListener(topics="product-created-events-topic")
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handler(ProductCreatedEvent productCreatedEvent){
            LOGGER.info("Received a new Event: " + productCreatedEvent.getTitle());
    }
}
