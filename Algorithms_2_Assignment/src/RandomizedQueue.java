import edu.princeton.cs.algs4.*;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int N = 0;
	private Item[] rArray;

	public RandomizedQueue() {
		rArray = (Item[]) new Object[1];
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	private void resize(int capacity) {
		assert capacity >= N;
		Item[] copy = (Item[]) new Object[capacity];
		for (int j = 0; j < N; j++) {
			copy[j] = rArray[j];
		}
		rArray = copy;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException("Cannot add a null item!");
		}
		if (N == rArray.length) {
			resize(2 * rArray.length);
		}
		rArray[N++] = item;
	}

	public Item dequeue() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException("Cannot remove item from an empty queue!");
		}
		int lo = StdRandom.uniform(N);
		Item rm = rArray[lo];
		rArray[lo] = rArray[--N];
		rArray[N] = null;
		if (N > 0 && N == rArray.length) {
			resize(rArray.length / 4);
		}
		return rm;
	}

	public Item sample() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException("Cannot show an item from an empty queue!");
		}
		int lo = StdRandom.uniform(N);
		return rArray[lo];
	}

	public Iterator<Item> iterator() {
		return new myIterator();
	}

	private class myIterator implements Iterator<Item> {
		private int[] randomIndex;
		private int i;

		public myIterator() {
			i = 0;
			randomIndex = new int[N];
			for (int j = 0; j < N; j++) {
				randomIndex[j] = j;
			}
			StdRandom.shuffle(randomIndex);
		}

		public boolean hasNext() {
			return i < N;
		}

		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException(
						"Cannot show next item when there are no more items to return!");
			}
			return rArray[randomIndex[(i++)]];
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException("Cannot call remove() in Iterator!");
		}
	}

	public static void main(String[] args) {
		RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
		test.enqueue(2);
		System.out.println(test.size());
	}
}
