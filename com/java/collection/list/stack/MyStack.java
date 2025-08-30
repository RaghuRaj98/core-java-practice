package com.java.collection.list.stack;

public class MyStack<E> {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public MyStack() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void push(E item) {
        ensureCapacity();
        elements[size++] = item;
    }

    public E pop() {
        if (isEmpty()) throw new RuntimeException("Stack is empty!");
        E e = (E) elements[--size];
        elements[size] = null; // avoid memory leak
        return e;
    }

    public E peek() {
        if (isEmpty()) throw new RuntimeException("Stack is empty!");
        return (E) elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newArr = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newArr, 0, elements.length);
            elements = newArr;
        }
    }
}
