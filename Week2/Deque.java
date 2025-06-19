import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // mamory: 48n+192 bytes
    private class Node {
        private Node last;
        private Node next;
        private Item val;
    }
    private Integer dequeSize;
    private Node firstNode;
    private Node lastNode;

    // construct an empty deque
    public Deque() {
        dequeSize = 0;
        firstNode = null;
        lastNode = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return dequeSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            throw e;
        }
        Node newNode = new Node();
        newNode.val = item;
        if (firstNode != null) {
            firstNode.last = newNode;
            newNode.next = firstNode;
            firstNode = newNode;
        }else {
            firstNode = newNode;
        }

        if (lastNode == null) {
            lastNode = newNode; 
        }
        dequeSize += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            throw e;
        }
        Node newNode = new Node();
        newNode.val = item;
        if (lastNode != null) {
            lastNode.next = newNode;
            newNode.last = firstNode;
            lastNode = newNode;
        }else {
            lastNode = newNode;
        }

        if (firstNode == null) {
            firstNode = newNode;
        }
        dequeSize += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            NoSuchElementException e = new NoSuchElementException();
            throw e;
        }
        if (dequeSize == 1) {
            Node temp = firstNode;
            firstNode = null;
            lastNode = null;
            dequeSize -= 1;
            return temp.val;
        }
        Node item = firstNode;
        if (firstNode.next != null) {
            firstNode.next.last = null;
            firstNode = firstNode.next;
        }
        dequeSize -= 1;
        return item.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            NoSuchElementException e = new NoSuchElementException();
            throw e;
        }
        Node item = lastNode;
        if (lastNode.last != null) {
            lastNode.last.next = null;
            lastNode = lastNode.last;
        }
        dequeSize -= 1;
        return item.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator(firstNode);
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node head;
        DequeueIterator(Node head) {
            this.head = head;
        }

        @Override
        public boolean hasNext() {
//            if (this.head == null) return false;
            return head != null;
        }

        @Override
        public Item next() {
            if (head == null) {
                throw new NoSuchElementException();
            }
            Item val = head.val;
            head = head.next;
            return val;
        }

        @Override
        public void remove() {
            UnsupportedOperationException e = new UnsupportedOperationException();
            throw e;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String op = StdIn.readString();
            if (op.equals("+f")) {
                String val = StdIn.readString();
                d.addFirst(val);
            }
            if (op.equals("+l")) {
                String val = StdIn.readString();
                d.addLast(val);
            }
            if (op.equals("-f")) {
                d.removeFirst();
            }
            if (op.equals("-l")) {
                d.removeLast();
            }
        }
        Iterator<String> iterator = d.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }

}