import edu.princeton.cs.algs4.*;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private int N = 0;
	private Node first, last;

	private class Node {
		private Item item;
		private Node next;
		private Node pre;
	}

	public Deque() {
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return N;
	}

	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException("Cannot add a null item!");
		}
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.pre = null;
		if (last == null) { // condition must be this. Cannot be
							// "if(isEmpty())"!!! because it can't be empty now
			last = first;
		} else {
			first.next = oldFirst;
			oldFirst.pre = first;
		}
		N++;
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException("Cannot add a null item!");
		}
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()) {
			last.pre = null;
			first = last;
		} else {
			oldLast.next = last;
			last.pre = oldLast;
		}
		N++;
	}

	public Item removeFirst() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException("Cannot remove an item from an empty deque!");
		}
		Item rm = first.item;
		first = first.next;
		first.pre = null;
		if (isEmpty()) {
			last = null;
		}
		N--;
		return rm;
	}

	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException("Cannot remove an item from an empty deque!");
		}
		Item rm = last.item;
		last = last.pre;
		last.next = null;
		if (isEmpty()) {
			first = null;
		}
		N--;
		return rm;
	}

	public Iterator<Item> iterator() {
		return new dequeIterator();
	}

	private class dequeIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			if (current == null) {
				throw new java.util.NoSuchElementException("Cannot call next() when there are no more items to return!");
			}
			Item cur = current.item;
			current = current.next;
			return cur;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException("Cannot call remove() in Iterator!");
		}
	}

	public static void main(String[] args) {
		Deque<String> myDeque = new Deque<String>();
		String[] str = new String[] { "Jrui", "Long", "Jason" };
		myDeque.addFirst(str[0]);
		myDeque.addLast(str[2]);
		myDeque.addFirst(str[1]);

		Iterator<String> test = myDeque.iterator();
		while (test.hasNext()) {
			String s = test.next();
			StdOut.println(s);
		}
	}
}
