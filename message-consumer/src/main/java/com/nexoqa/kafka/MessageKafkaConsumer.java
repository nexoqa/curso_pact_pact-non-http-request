package com.nexoqa.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexoqa.model.Contact;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.util.Properties;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;


public class MessageKafkaConsumer {

  final private String brokers;
  final private String topicName;

  MessageKafkaConsumer(String brokers, String topicName) {
    this.brokers = brokers;
    this.topicName = topicName;
  }

  public int consume() {
    // Create a consumer
    KafkaConsumer consumer;
    // Configure the consumer
    Properties properties = new Properties();
    // Point it to the brokers
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
    // Set the consumer group (all consumers must belong to a group).
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "MessageKafkaConsumer");
    // Set how to serialize key/value pairs
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.connect.json.JsonDeserializer");

    // When a group is first created, it has no offset stored to start reading from. This tells it to start
    // with the earliest record in the stream.
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


    consumer = new KafkaConsumer<>(properties);
    int count = 0;
    try {
      Duration interval = Duration.ofMinutes(2);
      consumer.subscribe(Collections.singletonList(topicName));
      while (true) {
        // Poll for records
        ConsumerRecords records = consumer.poll(interval);
        
        for (Object objectRecord : records) {
          ConsumerRecord record = (ConsumerRecord) objectRecord;
          System.out.println("Key: " + record.key() + ", Value: " + record.value());
          System.out.println("Partition: " + record.partition() + ", Offset: " + record.offset());
          
          ObjectMapper objectMapper = new ObjectMapper();
          Contact contact = objectMapper.treeToValue((JsonNode)record.value(), Contact.class);
          
          System.out.println(contact);

        }

        // for (Object partition : records.partitions()) {
        //   List partitionRecords = records.records((TopicPartition) partition);

        //   for (int index = 0; index < partitionRecords.size(); index++) {
        //     // ConsumerRecord record = (ConsumerRecord) partitionRecords.get(index);
        //     // JsonNode jsonNode = (JsonNode) record.value();
        //     // System.out.println(mapper.treeToValue(jsonNode, Contact.class));
        //     // System.out.printf(index + ":offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
        //     //     record.value());

            
        //   }
          // long lastOffset = ((ConsumerRecord)partitionRecords.get(partitionRecords.size() - 1)).offset();
          // consumer.commitSync(Collections.singletonMap((TopicPartition) partition, new OffsetAndMetadata(lastOffset + 1)));
        // }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      consumer.close();
    }
    return count;
  }
    
  public static void main(String[] args) throws IOException {
        
    MessageKafkaConsumer consumer = new MessageKafkaConsumer("localhost:9092", "testTopic");
    System.out.println(consumer.consume());
        
  }
}