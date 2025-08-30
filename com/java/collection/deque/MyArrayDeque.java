package com.java.collection.deque;

class MyArrayDeque {
    private Object[] elements;
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public MyArrayDeque(int capacity) {
        elements = new Object[capacity];
    }

    public void offerFirst(Object e) {
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = e;
        size++;
    }

    public void offerLast(Object e) {
        elements[tail] = e;
        tail = (tail + 1) % elements.length;
        size++;
    }

    public Object pollFirst() {
        if (size == 0) return null;
        Object val = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return val;
    }

    public Object pollLast() {
        if (size == 0) return null;
        tail = (tail - 1 + elements.length) % elements.length;
        Object val = elements[tail];
        elements[tail] = null;
        size--;
        return val;
    }
}
