package com.java.threads;

class ReportTask extends Thread {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("⚠️ Report generation interrupted!");
                return;
            }
            System.out.println("📊 Generating report part " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("⚠️ Report task interrupted during sleep.");
                return;
            }
        }
        System.out.println("✅ Report generated successfully!");
    }
}

public class InterruptExample {
    public static void main(String[] args) throws InterruptedException {
        ReportTask report = new ReportTask();
        report.start();

        Thread.sleep(3000);
        System.out.println("❌ Cancelling report...");
        report.interrupt();
    }
}
