package com.java.threads;

class BankTask extends Thread {
    private String taskName;

    public BankTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started: " + taskName);

        try {
            if (taskName.equals("Money Transfer")) {
                System.out.println("Processing money transfer...");
                Thread.sleep(2000); // simulate processing delay
                System.out.println("✅ Money transfer completed!");
            } else if (taskName.equals("Balance Check")) {
                System.out.println("Fetching account balance...");
                Thread.sleep(1000);
                System.out.println("💰 Current balance: ₹50,000");
            }
        } catch (InterruptedException e) {
            System.out.println(taskName + " was interrupted!");
        }

        System.out.println(Thread.currentThread().getName() + " finished: " + taskName);
    }
}

public class BankingThreads {
    public static void main(String[] args) throws InterruptedException {
        BankTask t1 = new BankTask("Money Transfer");
        BankTask t2 = new BankTask("Balance Check");

        Thread.sleep(1000); // simulate some delay before starting threads
        // set names for better readability
        t1.setName("Thread-Transfer");
        t2.setName("Thread-Balance");

        t1.start();


        try {
            // wait for transfer to finish before main continues
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        System.out.println("Is Balance thread still alive? " + t2.isAlive());
        System.out.println("Main thread finished ✅");
    }
}
