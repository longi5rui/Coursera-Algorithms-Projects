import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @Date: 08/09/2015 - from 8:00 am to 18:22 pm finished
 * @Author: Jinglong Li
 */

public class PercolationStats {
	private int t;
	private double[] thresholds; // creat a array to store every result about
									// threshold

	public PercolationStats(int N, int T) {
		t = T;
		thresholds = new double[t];
		for (int c = 0; c < t; c++) { // iterate t times
			int count = 0;
			Percolation test = new Percolation(N);
			while (!test.percolates()) {
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				if (!test.isOpen(i, j)) { // if the cell is blocked, I open the
											// cell. Otherwise jump out this
											// inner loop and restart
					test.open(i, j);
					count++;
				} else {
					continue;
				}
			}
			thresholds[c] = (double) count / (double) (N * N);
		}
	}

	public double mean() {
		return StdStats.mean(this.thresholds);
	}

	public double stddev() {
		return StdStats.stddev(this.thresholds);
	}

	public double confidenceLo() {
		double mean = mean();
		double Kexi = stddev();
		return mean - 1.96 * Kexi / Math.sqrt(t);
	}

	public double confidenceHi() {
		double mean = mean();
		double Kexi = stddev();
		return mean + 1.96 * Kexi / Math.sqrt(t);
	}

	// just run the code and follow the instructions. run the code again if you
	// want input different N and T
	public static void main(String[] args) {
		System.out.println("***Please input N-N grid's N and T which is the times of independent experiments***");
		System.out.print("N = ");
		int x = StdIn.readInt();
		System.out.print("T = ");
		int y = StdIn.readInt();
		if (x <= 0 || y <= 0) {
			throw new java.lang.IllegalArgumentException("N or T out of bounds");
		}
		PercolationStats Test = new PercolationStats(x, y);
		System.out.printf("mean                     = %.16f\n", Test.mean());
		System.out.printf("stddev                   = %.16f\n", Test.stddev());
		System.out.printf("95%% confidence Interval  = %.16f, %.16f\n", Test.confidenceLo(), Test.confidenceHi());
	}
}
