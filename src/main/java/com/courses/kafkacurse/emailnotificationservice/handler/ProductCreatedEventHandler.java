package com.courses.kafkacurse.emailnotificationservice.handler;

import com.courses.kafkacurse.corekafkalibrary.ProductCreatedEvent;
import com.courses.kafkacurse.emailnotificationservice.exceptions.NotRetryableException;
import com.courses.kafkacurse.emailnotificationservice.exceptions.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
// This annotation can be used in classes or method intended to work as target to new coming Kafka messages
// For multiple topics -> (topics={"topic1", "topic2"})
//@KafkaListener(topics="product-created-events-topic", groupId = "product-created-events") //For Consumer Groups
@KafkaListener(topics="product-created-events-topic")
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate;

    public ProductCreatedEventHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @KafkaHandler
    public void handler(ProductCreatedEvent productCreatedEvent){
        LOGGER.info("Received a new Event: " + productCreatedEvent.getTitle());

        String requestUrl = "http://localhost:8082/response/200"; // We need a mock service apart running to be able to test this

        try{
            ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);
            if(response.getStatusCode().value() == HttpStatus.OK.value()) {
                LOGGER.info("Received response from a remote service: " + response.getBody());
            }
        }catch(ResourceAccessException rae){
            LOGGER.error(rae.getMessage());
            throw new RetryableException(rae);
        }catch (HttpServerErrorException hsee){
            LOGGER.error(hsee.getMessage());
            throw new NotRetryableException(hsee);
        }catch(Exception ex){
            LOGGER.error(ex.getMessage());
            throw new NotRetryableException(ex);
        }
    }
}
