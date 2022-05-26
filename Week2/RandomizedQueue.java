import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Item[] items;
    private int tail = 0;// last point
    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 1;
        this.items = (Item[]) new Object[1];
        this.tail = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.tail == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.tail;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.tail >= this.size) {
            // extend array;
            int originSize = this.size;
            this.size = this.size * 2;
            Item[] newItems = (Item[]) new Object[this.size];
            for (int i = 0; i < originSize; i++) {
                newItems[i] = this.items[i];
            }
            this.items = newItems;

        }
        this.items[this.tail] = item;
        this.tail += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(this.tail);
        Item res = this.items[idx];
        for (int i = idx; i < this.tail-1; i++) {
            this.items[i] = this.items[i+1];
        }
        this.tail -= 1;
        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(this.tail);
        return this.items[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this.items,this.tail);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int last = 0;
        private Item[] items;
        private int size = 0;
        RandomizedQueueIterator(Item[] items, int size) {
            this.items = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                this.items[i] = items[i];
            }
            StdRandom.shuffle(this.items);
            this.size = size;
        }

        public Iterator<Item> iterator() {
            return new RandomizedQueueIterator(this.items, this.size);
        }

        @Override
        public boolean hasNext() {
            return last<this.size;
        }

        @Override
        public Item next() {
            if (last >= this.size) {
                throw new NoSuchElementException();
            }
            Item res = this.items[last];
            last += 1;
            return res;
        }

        @Override
        public void remove() {
            UnsupportedOperationException e = new UnsupportedOperationException();
            throw e;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        StdOut.println(queue.sample());
        for (int i = 0; i < 4; i++) {
            StdOut.println(queue.dequeue());
        }
        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }

}
