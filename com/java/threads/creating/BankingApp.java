package com.java.threads.creating;

// Fund transfer thread by extending Thread
class FundTransferThread extends Thread {
    public void run() {
        System.out.println("💰 Processing fund transfer...");
        try {
            Thread.sleep(2000); // simulate time taken
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Fund transfer completed!");
    }
}

public class BankingApp {
    public static void main(String[] args) {
        FundTransferThread t1 = new FundTransferThread();
        t1.start(); // start fund transfer thread
    }
}
