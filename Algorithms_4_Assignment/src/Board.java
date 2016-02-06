import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;

public class Board {
	private final int[][] blocks;
	private final int N; // 矩阵长度/宽度

	public Board(int[][] blocks) { // 注意这种拷贝2维数组方法
		N = blocks.length; 
		this.blocks = new int[N][];
		for (int i = 0; i < N; i++) { // 0代表空的一个格子，不算block！
			this.blocks[i] = Arrays.copyOf(blocks[i], N);
		}
	}

	public int dimension() {
		return N;
	}

	public int hamming() { // 当前图与最终图不同的blocks数目，不算0，它不是block
		int count = 0;
		int temp = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != temp++)
					count++;
				if (temp == N * N) // 最终图中最后1个定为0,不算block不能计算
					break;
			}
		}
		return count;
	}

	public int manhattan() { //看当前图的每个block里的数字要走多少步才能到最终图中自己应该在的位置
		int q, r;
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;  // 不算0的
				q = blocks[i][j] / N;
				r = blocks[i][j] % N;
				if (q != 0 && r == 0) { // 说明当前位置在最右边1列，此列用q、r余数法会有进位bug，要调整下
					q -= 1;
					r = blocks[i][j] - q * N;
				}
				sum += Math.abs(q - i) + Math.abs((r - 1) - j);
			}
		}
		return sum;
	}

	public boolean isGoal() {
		return this.hamming() == 0;
	}

	public Board twin() { // 8-Puzzle问题可能存在无解的情况，此时任意换它当中的2个block一定有解，但不能换0，它不是block
		int[][] twin = new int[N][];
		for (int i = 0; i < N; i++)
			twin[i] = Arrays.copyOf(blocks[i], N);
		if (twin[0][0] != 0 && twin[0][1] != 0)
			exch(twin, 0, 0, 0, 1);
		else
			exch(twin, 1, 0, 1, 1);
		return new Board(twin);
	}

	public boolean equals(Object y) { // 根据要求的标准equals()
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (this.getClass() != y.getClass())
			return false;
		Board that = (Board) y;
		if (this.N != that.N)
			return false;
		if (!Arrays.deepEquals(this.blocks, that.blocks))
			return false;
		return true;
	}

	// 找到所有可能的下一步的情况(移动一次后)
	public Iterable<Board> neighbors() { // 返回Queue、Stack等都行，都是Iterable，根据实际需求选择
		Queue<Board> q = new Queue<Board>();
		boardItr(q);
		return q;
	}

	private void boardItr(Queue<Board> q) {
		int i = 0, j = 0;
		boolean find = false;
		for (int k = 0; k < N; k++) {
			for (int m = 0; m < N; m++) {
				if (blocks[k][m] == 0) {
					i = k;
					j = m;
					find = true;
					break;
				}
			}
			if (find)
				break;
		}

		if (j - 1 >= 0) { // 再遇到类似情况，像这样讨论4边即可，别再傻傻地讨论N种了！
			Board temp = new Board(blocks);
			exch(temp.blocks, i, j, i, j - 1);
			q.enqueue(temp);
		}
		if (j + 1 <= N - 1) {
			Board temp = new Board(blocks);
			exch(temp.blocks, i, j, i, j + 1);
			q.enqueue(temp);
		}
		if (i - 1 >= 0) {
			Board temp = new Board(blocks);
			exch(temp.blocks, i, j, i - 1, j);
			q.enqueue(temp);
		}
		if (i + 1 <= N - 1) {
			Board temp = new Board(blocks);
			exch(temp.blocks, i, j, i + 1, j);
			q.enqueue(temp);
		}
	}

	public String toString() { // 利用 StringBuilder 来构造toString()
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	private void exch(int[][] a, int i1, int j1, int i2, int j2) {
		int temp = a[i1][j1];
		a[i1][j1] = a[i2][j2];
		a[i2][j2] = temp;
	}

	public static void main(String[] args) {
		int[][] test = { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 5 } };
		// int[][] test = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		Board b = new Board(test);
		Iterable<Board> q = b.neighbors();
		for (Board item : q) {
			System.out.println(item);
		}
		System.out.println(b.twin());
	}
}