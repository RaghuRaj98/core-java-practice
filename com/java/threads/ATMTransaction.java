package com.java.threads;

class ATMTransaction extends Thread {
    private String customerName;
    private int amount;

    public ATMTransaction(String customerName, int amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            System.out.println(customerName + " inserting ATM card...");
            Thread.sleep(2000); // 2 sec delay for card reading

            System.out.println(customerName + " entering PIN...");
            Thread.sleep(1500); // 1.5 sec delay for PIN verification

            System.out.println("Connecting to bank server for " + customerName + "...");
            Thread.sleep(3000); // 3 sec delay for server communication

            System.out.println("Processing withdrawal of ₹" + amount + " for " + customerName + "...");
            Thread.sleep(2000); // 2 sec delay for transaction processing

            System.out.println("✅ Cash dispensed to " + customerName + " [₹" + amount + "]");
            System.out.println("----------------------------------------------------");

        } catch (InterruptedException e) {
            System.out.println(customerName + "'s transaction was interrupted!");
        }
    }

    public static void main(String[] args) {
        ATMTransaction t1 = new ATMTransaction("Raghav", 5000);
        ATMTransaction t2 = new ATMTransaction("Raj", 2000);

        t1.start();
        t2.start();
    }
}
