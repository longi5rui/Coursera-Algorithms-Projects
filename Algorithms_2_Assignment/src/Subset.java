import edu.princeton.cs.algs4.*;

public class Subset {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> test = new RandomizedQueue<String>();

		while (!StdIn.isEmpty()) {
			if (test.size() <= k) {
				test.enqueue(StdIn.readString());
			} else {
				break;
			}
		}
		for (int i = 0; i < k; i++) {
			System.out.println(test.dequeue());
		}
	}
}
