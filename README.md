# message_contract_testing_pact
Repository to learn message contract testing using Java Pact Library

# Start up pact broker
```
cd pact-broker
docker-compose up -d
```
# See pact broker logs
```
cd pact-broker
docker-compose logs -f broker_app
```

# Shut down pact broker
```
cd pact-broker
docker-compose down
```

# Start up Kafka
```
cd kafka
docker-compose up -d
```
# See Kafka logs
```
cd kafka
docker-compose logs -f broker
```

# Shut down Kafka
```
cd kafka
docker-compose down
```

# Create Kafka topic
```
docker exec broker kafka-topics --bootstrap-server broker:9092 --create --topic testTopic
```


# To execute the consumer
```
cd message-consumer
mvn clean install
mvn exec:java@consumer
```

# To execute the producer
```
cd message-producer
mvn clean install
mvn exec:java@producer
```

# Ejercicio

- Modificar la definión del objeto `Contact` añadiendo insternamente un objeto `Address`.
