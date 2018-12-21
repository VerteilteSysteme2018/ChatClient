package edu.hm.dako.chat.client_jms.gui;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


    int numberOfClients = 10;
    int numberOfMessages = 20;
    int numberOfAllRequests = numberOfClients * numberOfMessages;


    /**
     * Startzeit ermitteln
     */
    Calendar cal = Calendar.getInstance();
    long startTime = cal.getTimeInMillis();



    public static void main(String args[]) {

        int clientNumber = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(clientNumber);

        for (int i = 0; i < clientNumber; i++) {
            String username = "User-" + i;

            executorService.submit(new TestClient(username, "localhost", "8080"));

                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.out.print("can't sleep");
                }

        }
    }
}