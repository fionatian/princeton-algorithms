/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;


    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported with Iterator!");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("deque is empty!");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    public Deque() {

        first = null;
        last = null;
        size = 0;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("addFirst null to deque!");
        }

        Node newNode = new Node(item, first, null);
        if (first == null) {
            last = newNode;
        }
        else {
            first.previous = newNode;
        }
        first = newNode;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("addLast null to deque!");
        }
        Node newNode = new Node(item, null, last);
        if (last == null) {
            first = newNode;
        }
        else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty!");
        }

        Item item = first.item;
        first = first.next;

        size--;

        //last element to remove
        if (size == 0) {
            last = null;
        }
        else {
            first.previous = null;
        }

        return item;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty!");
        }
        Item item = last.item;
        last = last.previous;

        size--;

        //last element to remove
        if (size == 0) {
            first = null;
        }
        else {
            last.next = null;
        }

        return item;
    }


    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<Integer>();
        deque.isEmpty();
        deque.isEmpty();
        deque.isEmpty();
        deque.addLast(4);
        deque.removeLast();

        deque.isEmpty();
        deque.isEmpty();
        deque.isEmpty();
        deque.addFirst(4);
        deque.removeFirst();

        Deque<String> dq = new Deque<String>();
        dq.addFirst("a");
        dq.addFirst("b");
        dq.addLast("last1");
        dq.addLast("last2");


        StdOut.println("size is " + dq.size());
        Iterator<String> it = dq.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }


        StdOut.println("removeFirst: " + dq.removeFirst());

        for (String s : dq) {
            StdOut.println(s);
        }


    }
}
