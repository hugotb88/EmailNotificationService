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


## Three ways to configure a CONSUMER GROUP

Remember that to scale an app, you should not create more consumer instances than partition instances.

Number of Consumers <= Number of partitions in your topic. \
Other way the extra instances will be `_idle_`

1. In the Kafka Listener
`@KafkaListener(topics="product-created-events-topic", groupId = "product-created-events") //For Consumer Groups`

2. In the Kafka Consumer Configuration class.
   * ConsumerFactory method, ConsumerConfig parameter.
   * `configuration.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));`

3. From the .properties file
`spring.kafka.consumer.group-id=product-created-events`

One way to confirm that each instance is assigned to a partition is in the console when we start the application.
(Re balancing)

you will see something like this:
`2025-02-07T14:33:19.207-06:00  INFO 4532 --- [EmailNotificationService] [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : product-created-events: partitions assigned: [product-created-events-topic-0]`
