package edu.hm.dako.chat.benchmarking_kafka;


import java.io.IOException;

public class Test {

    public static void main(String args[]) throws IOException {

        KafkaChatClient kafkaChatClient = new KafkaChatClient();

        kafkaChatClient.tell("Anna", "Hallooooo");

        kafkaChatClient.tell("Peter", "Sisisis");

        //KafkaSimpleMessageListenerThread kafkaSimpleMessageListenerThread = new KafkaSimpleMessageListenerThread("Anna");


    }
}
