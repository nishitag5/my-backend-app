package com.example.demo.service;

import com.azure.messaging.servicebus.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final ServiceBusSenderClient senderClient;
    private final boolean notificationsEnabled;

    public NotificationService(
            @Value("${azure.servicebus.connection-string}") String connectionString,
            @Value("${azure.servicebus.queue-name}") String queueName,
            @Value("${notifications.enabled:false}") boolean notificationsEnabled) {

        this.notificationsEnabled = notificationsEnabled;

        if (notificationsEnabled) {
            this.senderClient = new ServiceBusClientBuilder()
                    .connectionString(connectionString)
                    .sender()
                    .queueName(queueName)
                    .buildClient();
        } else {
            this.senderClient = null;
            System.out.println("[INFO] Azure notifications are disabled for local dev.");
        }
    }

    public void sendMessage(String message) {
        if (!notificationsEnabled) {
            System.out.println("[INFO] Skipped notification: " + message);
            return;
        }
        senderClient.sendMessage(new ServiceBusMessage(message));
    }
}
