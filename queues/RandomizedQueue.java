/* *****************************************************************************
 *  Name: Yuan Tian
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] queue;

    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    private void resize(int len) {
        Item[] copy = (Item[]) new Object[len];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public int size() {
        return size;
    }

    // add an item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("enqueue null to deque!");
        }
        if (queue.length == size) {
            resize(size * 2);
        }
        queue[size++] = item;
    }


    // remove and return a random item
    public Item dequeue() {
        Item item;
        if (size <= 0) {
            throw new NoSuchElementException("RandomizedQueue is empty!");
        }
        int p = StdRandom.uniform(0, size);
        item = queue[p];
        queue[p] = queue[size - 1];
        queue[size - 1] = null;
        size--;

        if (size * 4 == queue.length) {
            resize(queue.length / 2);
        }

        return item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        Item item;
        if (size <= 0) {
            throw new NoSuchElementException("RandomizedQueue is empty!");
        }
        int p = StdRandom.uniform(0, size);
        item = queue[p];

        return item;
    }


    private class RandomIterator implements Iterator<Item> {

        private final int[] order;
        private int current;

        RandomIterator() {
            current = 0;
            order = StdRandom.permutation(size);
        }

        public boolean hasNext() {
            return (current < size);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return in iterator!");
            }
            return queue[order[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported with Iterator!");
        }

    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        for (int i = 0; i < 10; i++) {
            rq.enqueue((char) ('a' + i) + "");
        }

        for (String s : rq) {
            StdOut.print(s + " ");
        }
        StdOut.println();

        for (String s : rq) {
            StdOut.print(s + " ");
        }
        StdOut.println();


        for (int i = 0; i < 10; i++) {
            StdOut.print(rq.dequeue() + " ");
        }
        StdOut.println();

        for (String s : rq) {
            StdOut.print(s + " ");
        }


    }
}
