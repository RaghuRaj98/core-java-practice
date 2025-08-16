package com.java.threads;

class DownloadTask extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Downloading part " + i);
            try {
                Thread.sleep(1000); // simulate time to download
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("✅ Download completed!");
    }
}

public class SuspendResumeExample {
    public static void main(String[] args) throws InterruptedException {
        DownloadTask t = new DownloadTask();
        t.start();

       // Thread.sleep(2000);
        System.out.println("⏸ Pausing download...");
        //t.suspend();   // ⚠️ Deprecated, may cause deadlock

        //Thread.sleep(3000);
        System.out.println("▶️ Resuming download...");
        //t.resume();    // ⚠️ Deprecated

        t.join();
        System.out.println("Main thread finished.");
    }
}
