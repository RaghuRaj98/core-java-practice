package com.java.collection.queue.priorityqueqe;

import java.util.*;

class Task {
    String name;
    int priority; // smaller number = higher priority

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return name + "(P" + priority + ")";
    }
}

public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Task> queue = new PriorityQueue<>(Comparator.comparingInt(t -> t.priority));

        queue.offer(new Task("HighValueTransaction", 1));
        queue.offer(new Task("RegularTransfer", 3));
        queue.offer(new Task("LowValueTransaction", 5));

        while (!queue.isEmpty()) {
            System.out.println("Processing: " + queue.poll());
        }
    }
}
