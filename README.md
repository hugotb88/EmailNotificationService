# ProductsRepository
Spring Boot microservice of a Kafka Consumer for learning purposes.

### Configurations
Take a look into `KafkaConsumerConfiguration` and `application.properties` to see how the Consumer was configured


## Dead Letter Topic (DLT)
Its a place where we send messages that were not processed due an error.

Errors like:
* When the message is sent in a different format and the Consumer cant deserialize it.
    * e.g. Deserializer configured for JSON and receives an String.
* We can handle the error to only trigger an Exception once with the `ErrorHandlingDeserializer` class
  * This will only ignore the message and it will display the Exception once,
  avoiding to consume all the resources in an infinite loop that never ends trying to deserialize the message.

Dead Letter Topic can receive all those messages that are ignored, you can do this when you are configuring the ErrorHandlingDeserializer.
We can see the messages of the DLT later to choose what we want to do.

The convention for the DLT name is the following, only adding a "DLT" at the end.

`product-created-events-topic.DLT` -> old versions \
`product-created-events-topic-dlt` -> new versions

Example of command to see the messages in the DLT:

`./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic product-created-events-topic-dlt --from-beginning --property print.key=true --property print.value=true`