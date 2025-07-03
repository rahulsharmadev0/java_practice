package design_patterns.factory_method;


/*
 *
 *
 * Notification Factory
 * --------------------------
 * Implement a notification system that sends messages via Email, SMS, or Push.
 * Steps:
 *   Create a Notification interface with send(String msg)
 *   Create 3 implementations: EmailNotification, SMSNotification, PushNotification
 *   Create a factory class NotificationFactory with getNotification(String type)
 *   Client should use only the interface
 */


import static utils.IO.printf;

enum MessageType {
    EMAIL, SMS, PUSH
}

interface Notification {
    void send(String msg);
}

class EmailNotification implements Notification {
    @Override
    public void send(String msg) {
        printf("Sending Email: " + msg);
    }
}

class SMSNotification implements Notification {
    @Override
    public void send(String msg) {
        printf("Sending SMS: " + msg);
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String msg) {
        printf("Sending Push Notification: " + msg);
    }
}

class NotificationFactory {
    static Notification getNotification(MessageType type) {
        return switch (type) {
            case MessageType.EMAIL -> new EmailNotification();
            case MessageType.SMS -> new SMSNotification();
            case MessageType.PUSH -> new PushNotification();
        };
    }
}


public class NotificationFactoryDemo {

    public static void main(String[] args) {
        Notification factory = NotificationFactory.getNotification(
                MessageType.EMAIL
        );

        factory.send("Hello");
    }
}

