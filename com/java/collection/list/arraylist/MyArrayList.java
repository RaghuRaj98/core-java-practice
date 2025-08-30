package com.java.collection.list.arraylist;

class MyArrayList<E> {
    private Object[] elements;   // underlying array
    private int size;            // number of elements in list
    private static final int DEFAULT_CAPACITY = 10;

    // Constructor
    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // Add element at end
    public void add(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    // Get element by index
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    // Remove element by index
    public E remove(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E oldValue = (E) elements[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // clear last element
        return oldValue;
    }

    // Ensure array has enough space, else grow
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length + elements.length / 2; // 1.5x growth
            Object[] newArr = new Object[newCapacity];
            System.arraycopy(elements, 0, newArr, 0, elements.length);
            elements = newArr;
        }
    }

    // Helper: check valid index
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // Current size
    public int size() {
        return size;
    }
}
