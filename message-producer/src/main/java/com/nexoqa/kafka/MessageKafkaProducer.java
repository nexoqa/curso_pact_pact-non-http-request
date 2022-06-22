package com.nexoqa.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexoqa.model.Contact;
import com.nexoqa.model.Address;

import java.util.Properties;
import java.io.IOException;

public class MessageKafkaProducer
{
    final private String brokers;
    final private String topicName;

    MessageKafkaProducer(String brokers, String topicName) {
        this.brokers = brokers;
        this.topicName = topicName;
    }

    public void produce() throws IOException
    {

        // Set properties used to configure the producer
        Properties properties = new Properties();
        // Set the brokers (bootstrap servers)
        properties.setProperty("bootstrap.servers", brokers);
        // Set how to serialize key/value pairs
        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.connect.json.JsonSerializer");
        // specify the protocol for SSL Encryption This is needed for secure clusters
        //properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");

        KafkaProducer producer = new KafkaProducer(properties);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Contact contact = new Contact();
            contact.setContactId(1);
            contact.setFirstName("Bububombo");
            contact.setLastName("Tekateka");
            contact.setAddress(new Address("Street 10",10,"City 10"));
            JsonNode jsonNode = objectMapper.valueToTree(contact);
            ProducerRecord rec = new ProducerRecord(topicName, jsonNode);
            producer.send(rec);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            producer.close();
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        MessageKafkaProducer producer = new MessageKafkaProducer("localhost:9092", "testTopic");
        producer.produce();
            
    }

}