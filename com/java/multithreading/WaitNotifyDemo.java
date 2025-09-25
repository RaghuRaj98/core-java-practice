package com.java.multithreading;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyDemo {
    // Shared buffer
    static class Buffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        Buffer(int capacity) { this.capacity = capacity; }

        // Producer adds item
        public synchronized void produce(int item) throws InterruptedException {
            while (queue.size() == capacity) {
                System.out.println("Buffer full. Producer waiting...");
                wait(); // release lock, wait until notified
            }
            queue.offer(item);
            System.out.println("Produced: " + item + " | Queue=" + queue);
            notifyAll(); // wake consumers
        }

        // Consumer removes item
        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                System.out.println("Buffer empty. Consumer waiting...");
                wait(); // release lock, wait until notified
            }
            int item = queue.poll();
            System.out.println("Consumed: " + item + " | Queue=" + queue);
            notifyAll(); // wake producers
            return item;
        }
    }

    // Producer thread
    static class Producer extends Thread {
        private final Buffer buffer;
        Producer(Buffer buffer) { this.buffer = buffer; }

        @Override
        public void run() {
            int i = 1;
            try {
                while (i <= 10) {
                    buffer.produce(i++);
                    Thread.sleep(300); // simulate production time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer thread
    static class Consumer extends Thread {
        private final Buffer buffer;
        Consumer(Buffer buffer) { this.buffer = buffer; }

        @Override
        public void run() {
            try {
                while (true) {
                    buffer.consume();
                    Thread.sleep(500); // simulate consumption time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}
