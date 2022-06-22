package com.nexoqa.kafka;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.nexoqa.model.Contact;
import com.nexoqa.model.Address;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit.MessagePactProviderRule;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.messaging.MessagePact;


public class MessageKafkaConsumerTest {

    @Rule
    public MessagePactProviderRule mockProvider = new MessagePactProviderRule(this);
    private byte[] currentMessage;

    @Pact(provider = "MessageKafkaProducer", consumer = "MessageKafkaConsumer")
    public MessagePact createPact(MessagePactBuilder builder) {

        Contact contact = new Contact();
        contact.setContactId(1);
        contact.setFirstName("Bububombo");
        contact.setLastName("Tekateka");
        contact.setAddress(new Address("street 1",1,"City 1"));

        PactDslJsonBody body = new PactDslJsonBody()
            .integerType("contactId", contact.getContactId())
            .stringType("firstName", contact.getFirstName())
            .stringType("lastName", contact.getLastName())
            .object("address")
            .stringType("street", contact.getAddress().getStreet())
            .integerType("number", contact.getAddress().getNumber())
            .stringType("city", contact.getAddress().getCity())
            .asBody();


        Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("contentType", "application/json");

        return builder.given("ContactProviderState")
                .expectsToReceive("a test contact")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }

    @Test
    @PactVerification(value="MessageKafkaProducer" )
    public void test() throws Exception {
        Assert.assertNotNull(new String(currentMessage));
    }

    public void setMessage(byte[] messageContents) {
        currentMessage = messageContents;
    }
}