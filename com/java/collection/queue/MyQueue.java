package com.java.collection.queue;

class MyQueue<E> {
    private Object[] elements;
    private int front = 0, rear = -1, size = 0;
    private static final int DEFAULT_CAPACITY = 5;

    public MyQueue() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void offer(E e) {
        if (size == elements.length) resize();
        rear = (rear + 1) % elements.length;
        elements[rear] = e;
        size++;
    }

    public E poll() {
        if (isEmpty()) return null;
        E e = (E) elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        return e;
    }

    public E peek() {
        return isEmpty() ? null : (E) elements[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        Object[] newArr = new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newArr[i] = elements[(front + i) % elements.length];
        }
        elements = newArr;
        front = 0;
        rear = size - 1;
    }
}
