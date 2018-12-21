package edu.hm.dako.chat.benchmarking_kafka;

import edu.hm.dako.chat.client.AbstractMessageListenerThread;
import edu.hm.dako.chat.client.ClientUserInterface;
import edu.hm.dako.chat.client.SharedClientData;
import edu.hm.dako.chat.client.SimpleMessageListenerThreadImpl;
import edu.hm.dako.chat.common.ChatMessage;
import edu.hm.dako.chat.common.ChatPDU;
import edu.hm.dako.chat.connection.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaSimpleMessageListenerThread extends AbstractMessageListenerThread {

    private static Log log = LogFactory.getLog(SimpleMessageListenerThreadImpl.class);

    private String threadName;
    private int numberOfMessagesToSend;

    public KafkaSimpleMessageListenerThread(ClientUserInterface userInterface, Connection con, SharedClientData sharedData, String threadName, int numberOfMessagesToSend) {

        super(userInterface, con, sharedData);
        this.threadName = threadName;
        this.numberOfMessagesToSend = numberOfMessagesToSend;
    }

    public void run() {

        //get Kafka Topic
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "edu.hm.dako.chat.common.ChatMessageDeserializer");
        properties.put("group.id", this.threadName);//"test-group");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("responseTopic");
        kafkaConsumer.subscribe(topics);
        Thread thread = new Thread(() -> {
            try {

                while (true) {
                    ConsumerRecords<String, ChatMessage> messages = kafkaConsumer.poll(1000);
                    for (ConsumerRecord<String, ChatMessage> omessage : messages) {
                        System.out.println("Message received " + omessage.value().toString());
                        ChatMessage message = omessage.value();
                        String chatName = message.getUserName();
                        String chatMessage = message.getMessage();

                        System.out.println(message.getUserName() + ":" + message.getMessage());

                        chatMessageEventAction(chatName);
                        chatMessageResponseAction(chatName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        
        
    }

    @Override
    protected void chatMessageResponseAction(ChatPDU receivedPdu) {
        // nothing to do for Kafka implementation

    }

    @Override
    protected void chatMessageEventAction(ChatPDU receivedPdu) {
        // nothing to do for Kafka implementation

    }

    @Override
    protected void loginResponseAction(ChatPDU receivedPdu) {
        // nothing to do for Kafka implementation

    }

    @Override
    protected void loginEventAction(ChatPDU receivedPdu) {
        // nothing to do for Kafka implementation
    }

    @Override
    protected void logoutEventAction(ChatPDU receivedPdu) {
        // nothing to do for Kafka implementation
    }

    @Override
    protected void logoutResponseAction(ChatPDU receivedPdu) {

        sharedClientData.eventCounter.getAndIncrement();
        int events = SharedClientData.logoutEvents.incrementAndGet();

        log.debug("LogoutEventCounter: " + events);
    }

    @Override
    protected void chatMessageResponseAction(String user) {

        userInterface.setLastServerTime(System.currentTimeMillis() / 1000000);
        userInterface.setLock(false);

    }

    @Override
    protected void chatMessageEventAction(String user) {

        sharedClientData.eventCounter.getAndIncrement();
        int events = SharedClientData.messageEvents.incrementAndGet();

        log.debug("MessageEventCounter: " + events);
    }

    @Override
    protected void loginResponseAction(String user) {

        // nothing to do for Kafka implementation 

    }

    @Override
    protected void loginEventAction(String user) {

        // nothing to do for Kafka implementation
    }

    @Override
    protected void logoutEventAction(String user) {

        // nothing to do for Kafka implementation
    }

    @Override
    protected void logoutResponseAction(String user) {

        // nothing to do for Kafka implementation
    }

}
