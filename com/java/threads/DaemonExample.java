package com.java.threads;

class AutoSave extends Thread {
    public void run() {
        while (true) {
            System.out.println("💾 Auto-saving document...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

public class DaemonExample {
    public static void main(String[] args) {
        AutoSave autoSave = new AutoSave();
        autoSave.setDaemon(true); // background service
        autoSave.start();

        System.out.println("✍️ Editing document...");
        try { Thread.sleep(5000); } catch (Exception e) {}
        System.out.println("✅ Closing editor (auto-save thread dies automatically).");
    }
}
