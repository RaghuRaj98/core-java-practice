package com.java.collection.queue.priorityqueqe;

class MyPriorityQueue {
    private int[] heap;
    private int size;

    public MyPriorityQueue(int capacity) {
        heap = new int[capacity];
        size = 0;
    }

    public void offer(int val) {
        if (size == heap.length) throw new RuntimeException("Queue full");
        heap[size] = val;
        heapifyUp(size);
        size++;
    }

    public int poll() {
        if (size == 0) throw new RuntimeException("Empty queue");
        int root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return root;
    }

    public int peek() {
        if (size == 0) throw new RuntimeException("Empty queue");
        return heap[0];
    }

    private void heapifyUp(int index) {
        while (index > 0 && heap[index] < heap[(index - 1) / 2]) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int left = 2 * index + 1, right = 2 * index + 2;

        if (left < size && heap[left] < heap[smallest]) smallest = left;
        if (right < size && heap[right] < heap[smallest]) smallest = right;

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
