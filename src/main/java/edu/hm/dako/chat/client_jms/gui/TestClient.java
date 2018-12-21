package edu.hm.dako.chat.client_jms.gui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.hm.dako.chat.client_jms.ClientController;
import edu.hm.dako.chat.common.ChatMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestClient implements Runnable {

    public TestClient(String username, String serverIP, String serverPort) {
        this.username = username;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    private String username;
    private String serverIP;
    private String serverPort;

    private boolean clientIsLoggedOut = true;

    private ClientController clientController;

    private Destination topic;


    public void loginUser() {

        clientController = new ClientController(username, serverIP, serverPort);

        try {
            if (clientController.login(username)) {
                clientIsLoggedOut = false;

                clientController.initializeConnectionFactory();
                clientController.lookupQueue();
                clientController.lookupTopic();

                //getMessages();
            } else {
                System.out.print("Login failed. Please try again!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutUser() {
        try {
            clientController.logout(username);
            clientIsLoggedOut = true;
        } catch (IOException e) {
            System.out.print("That didn't work." + "\n" + "Please try again!");
        }
    }

    public void sendMessage(String message) {
        clientController.sendMessageToQueue(username, message, "JMS");
    }

    public void getMessages() {
        Thread t1 = new Thread(() -> {
            JMSContext context = clientController.getJMSContext();
            Destination topic = clientController.getTopic();
            JMSConsumer consumer = context.createConsumer(topic);

            while (true) {
                if (clientIsLoggedOut) {
                    break;
                }

                String messages = consumer.receiveBody(String.class, 5000);

                if (messages != null) {
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(messages, JsonObject.class);

                    String name = json.get("userName").getAsString();
                    String message = json.get("message").getAsString();

                    long time = json.get("timestamp").getAsLong();

                    long RTT = System.currentTimeMillis() - time;

                    System.out.print("\n \n Roundtriptime:" + RTT + "for message " + message + " from " + name);
                }


                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        //get Kafka Topic
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "edu.hm.dako.chat.common.ChatMessageDeserializer");
        properties.put("group.id", "test-group");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("requestTopic");
        kafkaConsumer.subscribe(topics);
        Thread thread = new Thread(() -> {
            try {

                while (true) {
                    ConsumerRecords<String, ChatMessage> messages = kafkaConsumer.poll(100);
                    for (ConsumerRecord<String, ChatMessage> omessage : messages) {
                        System.out.println("Message received " + omessage.value().toString());
                        ChatMessage message = omessage.value();
                        String chatName = message.getUserName();
                        String chatMessage = message.getMessage();

                        System.out.println(message.getUserName() + ":" + message.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public boolean getIsClientLoggedOut() { return clientIsLoggedOut; }

    @Override
    public void run() {

        loginUser();

        getMessages();

        String message = "+++";

        for (int j = 0; j < 10; j++) {
            message = message + String.valueOf(j);
            sendMessage(message);

            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.print("can't sleep");
            }
        }

        logoutUser();

    }
}
