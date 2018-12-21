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



    public static void main(String args[]) throws IOException {

        Test test = new Test();

        KafkaChatClient kafkaChatClient = new KafkaChatClient();

        for (int i = 0; i < 10; i++) {
            String name = "Client" + i;

            kafkaChatClient.login(name);

            //test.getMessages(name);

            for (int j = 0; j < 100; j++) {
                String message = "+++" + j;

                kafkaChatClient.tell(name, message);
            }
        }

        //kafkaChatClient.tell("Peter", "Sisisis");


    }

    }
