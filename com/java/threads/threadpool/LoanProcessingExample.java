package com.java.threads.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class LoanTask implements Runnable {
    private String loanId;
    private String step;

    public LoanTask(String loanId, String step) {
        this.loanId = loanId;
        this.step = step;
    }

    @Override
    public void run() {
        System.out.println("Processing " + step + " for Loan: " + loanId 
                           + " on " + Thread.currentThread().getName());
        try { Thread.sleep(2500); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Completed " + step + " for Loan: " + loanId);
    }
}

public class LoanProcessingExample {
    public static void main(String[] args) {
        ExecutorService loanExecutor = Executors.newFixedThreadPool(3);

        String loanId = "LOAN-2001";

        loanExecutor.execute(new LoanTask(loanId, "Credit Score Check"));
        loanExecutor.execute(new LoanTask(loanId, "Employment Verification"));
        loanExecutor.execute(new LoanTask(loanId, "Document Verification"));

        loanExecutor.shutdown();
    }
}
