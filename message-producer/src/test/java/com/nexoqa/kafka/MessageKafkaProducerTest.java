package com.nexoqa.kafka;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.target.MessageTarget;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.target.Target;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexoqa.model.Contact;
import com.nexoqa.model.Address;

@RunWith(PactRunner.class)
@Provider("MessageKafkaProducer")
@PactBroker(
        host = "localhost", port = "80", consumers = "MessageKafkaConsumer"
)
public class MessageKafkaProducerTest {
  @TestTarget
  public final Target target = new MessageTarget();

  @State("ContactProviderState")
  public void ContactProviderState() {}

  @PactVerifyProvider("a test contact")
  public String verifyContact() {
    Contact contact = new Contact();
    contact.setContactId(1);
    contact.setFirstName("Bububombo");
    contact.setLastName("Tekateka");
    contact.setAddress(new Address("Street 1",1,"City 1"));
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.valueToTree(contact);
    return jsonNode.toPrettyString();
  }
}
