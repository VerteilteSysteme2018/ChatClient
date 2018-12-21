package edu.hm.dako.chat.benchmarking_kafka;


import edu.hm.dako.chat.common.ChatMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {

    public void getMessages(String name) {
        //get Kafka Topic
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "edu.hm.dako.chat.common.ChatMessageDeserializer");
        properties.put("group.id", name);//"test-group");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("responseTopic");
        kafkaConsumer.subscribe(topics);

        Thread t1 = new Thread(() -> {
            ConsumerRecords<String, ChatMessage> messages = kafkaConsumer.poll(1000);
            for (ConsumerRecord<String, ChatMessage> omessage : messages) {
                System.out.println("Message received " + omessage.value().toString());
                ChatMessage message = omessage.value();
                String chatName = message.getUserName();
                String chatMessage = message.getMessage();
                long RTT = System.currentTimeMillis() - message.getTimestamp();

                System.out.println("ROUNDTRIPTIME " + RTT);
                System.out.println(message.getUserName() + ":" + message.getMessage());
            }

        });
        t1.start();
    }


    public static void main(String args[]) throws IOException {

        Test test = new Test();

        KafkaChatClient kafkaChatClient = new KafkaChatClient();

        for (int i = 0; i < 10; i++) {
            String name = "Client" + i;

            kafkaChatClient.login(name);

            test.getMessages(name);

            for (int j = 0; j < 10; j++) {
                String message = "+++" + j;

                kafkaChatClient.tell(name, message);
            }
        }

        //kafkaChatClient.tell("Peter", "Sisisis");


    }

    }
