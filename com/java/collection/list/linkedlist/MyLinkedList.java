package com.java.collection.list.linkedlist;

class MyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    // Node class
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    // Add element at end
    public void add(E e) {
        Node<E> newNode = new Node<>(tail, e, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    // Get element by index
    public E get(int index) {
        checkIndex(index);
        Node<E> current;
        if (index < size / 2) { // traverse from head
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else { // traverse from tail
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current.data;
    }

    // Remove element by index
    public E remove(int index) {
        checkIndex(index);
        Node<E> current;
        if (index == 0) {
            current = head;
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null;
        } else if (index == size - 1) {
            current = tail;
            tail = tail.prev;
            if (tail != null) tail.next = null;
            else head = null;
        } else {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return current.data;
    }

    public int size() {
        return size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
