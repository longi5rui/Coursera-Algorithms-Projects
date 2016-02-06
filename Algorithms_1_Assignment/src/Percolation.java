import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @Date: 08/09/2015 - from 8:00 am to 18:22 pm finished
 * @Author: Jinglong Li To Professor: I really want to know the way how I can
 *          open the cell and link it to opened neighbors more conveniently with
 *          brief code... Could you please give me some hints?
 */

public class Percolation {
	private WeightedQuickUnionUF wuf; // creat an WQUF object "wuf"
	private boolean[] isOpen; // creat an boolean array to store which cell is
								// Open
	private boolean[] isFull; // creat an boolean array to store which cell is
								// Full
	private int N;

	public Percolation(int N) {
		if (N <= 0) {
			throw new java.lang.IllegalArgumentException("N out of bounds");
		}
		wuf = new WeightedQuickUnionUF(N * N + 1); // make it N*N+1 so I can use
													// wuf[0] as "virtual top
													// point" that links whole
													// cells in row one
		this.N = N;
		this.isOpen = new boolean[N * N + 1]; // accordingly I initialize isOpen
												// array to store state
		this.isFull = new boolean[N * N + 1];
		for (int x = 1; x < N * N + 1; x++)
			isOpen[x] = false; // false means "blocked"
		for (int y = 1; y < N * N + 1; y++)
			isFull[y] = false; // false means "not full"
		isOpen[0] = true; // make the virtual point to be open so it can be
							// linked
		isFull[0] = true; // virtual point is full
	}

	private int xyTo1D(int i, int j) { // use this method to transfer virtual
										// grid(2D array) to 1D array
		if (i <= 0 || i > N || j <= 0 || j > N) {
			throw new IndexOutOfBoundsException("row index i or j out of bounds");
		} else {
			return (i - 1) * N + j; // index should be rearrange
		}
	}

	public void open(int i, int j) {
		int loc = this.xyTo1D(i, j);
		if (i <= 0 || i > N || j <= 0 || j > N) {
			throw new IndexOutOfBoundsException("row index i or j out of bounds");
		} else { // I really get trouble here!! I try my best but I still don't
					// think a better way union opened neighbors
			isOpen[loc] = true; // mark the cell "open"
			if (i == 1) { // from now on I make union according to diffirent
							// situation
				if (j == 1) { // when the cell is upper-left
					wuf.union(0, loc); // link it to virtual top point
					if (isOpen(i, j + 1))
						wuf.union(loc, xyTo1D(i, j + 1));
					if (isOpen(i + 1, j))
						wuf.union(loc, xyTo1D(i + 1, j));
				} else if (j == N) { // when the cell is upper-right
					wuf.union(0, loc);
					if (isOpen(i, j - 1))
						wuf.union(loc, xyTo1D(i, j - 1));
					if (isOpen(i + 1, j))
						wuf.union(loc, xyTo1D(i + 1, j));
				} else { // when the cell is in row 1 but not upper-left or
							// upper-right
					wuf.union(0, loc);
					if (isOpen(i, j + 1))
						wuf.union(loc, xyTo1D(i, j + 1));
					if (isOpen(i + 1, j))
						wuf.union(loc, xyTo1D(i + 1, j));
					if (isOpen(i, j - 1))
						wuf.union(loc, xyTo1D(i, j - 1));
				}
			} // end if(i==1)
			if (i == N) {
				if (j == 1) { // when lower-left
					if (isOpen(i, j + 1))
						wuf.union(loc, xyTo1D(i, j + 1));
					if (isOpen(i - 1, j))
						wuf.union(loc, xyTo1D(i - 1, j));
				} else if (j == N) { // when lower-right
					if (isOpen(i, j - 1))
						wuf.union(loc, xyTo1D(i, j - 1));
					if (isOpen(i - 1, j))
						wuf.union(loc, xyTo1D(i - 1, j));
				} else { // when the cell is in row N but not lower-left or
							// lower-right
					if (isOpen(i, j + 1))
						wuf.union(loc, xyTo1D(i, j + 1));
					if (isOpen(i - 1, j))
						wuf.union(loc, xyTo1D(i - 1, j));
					if (isOpen(i, j - 1))
						wuf.union(loc, xyTo1D(i, j - 1));
				}
			} // end if(i==N)
			if (i != 1 && i != N && j == 1) { // when the cell is in most-left
												// column but not two end point
				if (isOpen(i + 1, j))
					wuf.union(loc, xyTo1D(i + 1, j));
				if (isOpen(i - 1, j))
					wuf.union(loc, xyTo1D(i - 1, j));
				if (isOpen(i, j + 1))
					wuf.union(loc, xyTo1D(i, j + 1));
			} // end if(i != 1 && i != N && j == 1)
			if (i != 1 && i != N && j == N) { // when the cell is in most-right
												// column but not two end point
				if (isOpen(i + 1, j))
					wuf.union(loc, xyTo1D(i + 1, j));
				if (isOpen(i - 1, j))
					wuf.union(loc, xyTo1D(i - 1, j));
				if (isOpen(i, j - 1))
					wuf.union(loc, xyTo1D(i, j - 1));
			} // end if(i != 1 && i != N && j == N)
			if (i != 1 && i != N && j != 1 && j != N) { // all the cells that
														// are not in the four
														// sides of 2D array
				if (isOpen(i + 1, j))
					wuf.union(loc, xyTo1D(i + 1, j));
				if (isOpen(i - 1, j))
					wuf.union(loc, xyTo1D(i - 1, j));
				if (isOpen(i, j + 1))
					wuf.union(loc, xyTo1D(i, j + 1));
				if (isOpen(i, j - 1))
					wuf.union(loc, xyTo1D(i, j - 1));
			} // end if (i != 1 && i != N && j != 1 && j != N)
		} // end else(when i and j in the bounds)
	}

	public boolean isFull(int i, int j) { // if the cell is connected to the
											// virtual up point, it is full
		return wuf.connected(0, xyTo1D(i, j));
	}

	public boolean isOpen(int i, int j) { // check the state array
		return isOpen[(i - 1) * N + j];
	}

	public boolean percolates() { // if one of the cell in the lower row is
									// full, then percolate
		boolean per = false;
		for (int j = 1; j <= N; j++) {
			if (this.isFull(N, j)) {
				per = true;
			}
		}
		return per;
	}

	public static void main(String[] args) { // main() testing
		int count = 0;
		Percolation test = new Percolation(200); // just make N 200 for testing
		while (!test.percolates()) {
			int i = StdRandom.uniform(1, 201); // generate a random number form
												// 1 to 200
			int j = StdRandom.uniform(1, 201);
			if (!test.isOpen(i, j)) {
				test.open(i, j);
				count++;
			} else {
				continue;
			}
		}
		System.out.println("This experiment use " + count + " times 'open' \noperations to percolate when N is 200!");
	}
}
