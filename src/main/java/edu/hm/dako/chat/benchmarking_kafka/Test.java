package edu.hm.dako.chat.benchmarking_kafka;


import java.io.IOException;

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

    }

}
