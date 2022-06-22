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
# See pact broker logs
```
cd kafka
docker-compose logs -f broker
```

# Shut down pact broker
```
cd kafka
docker-compose down
```

# To execute the producer
```
cd message-producer
mvn clean install
mvn exec:java@producer
```

# To execute the consumer
```
cd message-consumer
mvn clean install
mvn exec:java@consumer
```

# Ejercicio

- Modificar la definión del objeto `Contact` añadiendo insternamente un objeto `Address`.
