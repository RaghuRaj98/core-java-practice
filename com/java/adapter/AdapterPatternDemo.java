package com.java.adapter;

// ----- Target Interface ---

interface NotificationService{
    void sendNotification();
}

class SendSMS{
    void sendSMS(){
        System.out.println("SMS");
    }
}

class AdapterSMS implements NotificationService{
    SendSMS sendSMS;

    AdapterSMS(SendSMS sendSMS){
        this.sendSMS=sendSMS;
    }

    @Override
    public void sendNotification() {
        sendSMS.sendSMS();
    }
}

class EmailNotification implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("Sending Email: ");
    }

}

// ----- Demo -----
public class AdapterPatternDemo {
    public static void main(String[] args) {
        // Using Email directly
        NotificationService email = new EmailNotification();
        email.sendNotification();


    }
}

