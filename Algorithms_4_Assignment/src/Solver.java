import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator; //使用Comparator要import

public class Solver {
	private boolean isSolvable;
	private int moves;
	private MinPQ<Node> minPQ;
	private Stack<Board> solutionStack = new Stack<Board>();

	private class Node {
		private final Board board;
		private final int manhattan, moves, priority;
		private final Node pre; //指向上一步的Node，以追踪整个solution
		private final boolean isOriginal;

		public Node(Node x, Board b, int moves, boolean isOriginal) {
			this.pre = x;
			this.board = b;
			this.moves = moves;
			this.isOriginal = isOriginal; // 用来后面判断到底是original有解，还是其twin才有解
			this.manhattan = this.board.manhattan();
			this.priority = this.manhattan + moves;
		}

		public Comparator<Node> orderOfPriority() {
			return new OrderOfPriority(); // 返回比较器，作为参数传递给MinPQ<Node>()构造函数
		}

		private class OrderOfPriority implements Comparator<Node> {
			public int compare(Node a, Node b) {
				if (a.priority < b.priority)
					return -1;
				else if (a.priority > b.priority)
					return +1;
				else
					return 0;
			}
		}
	}

	public Solver(Board initial) { //在构造函数中 真正解出来
		if (initial == null)
			throw new java.lang.NullPointerException();
		Board twin = initial.twin();
		Node initialOri = new Node(null, initial, 0, true);
		Node initialTwin = new Node(null, twin, 0, false);

		minPQ = new MinPQ<Node>(initialOri.orderOfPriority());
		minPQ.insert(initialOri);
		minPQ.insert(initialTwin); //都放到这1个MinPQ里即可，用isOriginal区分

		while (true) {
			Node min = minPQ.delMin(); //每次取出差别最小的(即最可能的下一步)
			if (!min.board.isGoal()) { //若不是最终解
				for (Board item : min.board.neighbors()) { //把每一个下一步可能的情况放进MinPQ，除了冗余
					if (min.pre == null) { //如果当前是第1步
						Node next = new Node(min, item, min.moves + 1, min.isOriginal);
						minPQ.insert(next);
					} else if (!item.equals(min.pre.board)) { //不是第一步，则有pre，不能让neiborBoard与min.pre的Board一样，否则是冗余
						Node next = new Node(min, item, min.moves + 1, min.isOriginal);
						minPQ.insert(next);
					} else
						continue; //是冗余情况，跳过去
				}
			} else { // 若这次取出的是最终解
				isSolvable = min.isOriginal;
				this.moves = min.moves;
				while (min != null) { // 利用pre指针追踪把一路过来的solution排列起来
					this.solutionStack.push(min.board);
					min = min.pre;
				}
				break;
			}
		}
	}

	public boolean isSolvable() {
		return isSolvable;
	}

	public int moves() {
		if (!isSolvable())
			return -1;
		return this.moves;
	}

	public Iterable<Board> solution() {
		if (!isSolvable())
			return null;
		return solutionStack;
	}

	public static void main(String[] args) {
		int[][] test = { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } };
		// int[][] test = { { 1, 6, 2, 4}, { 5,0,3,8 },
		// {9,10,7,11},{13,14,15,12} };
		Board initial = new Board(test);
		Solver s = new Solver(initial);

		if (!s.isSolvable()) {
			StdOut.println("No solution possible!");
		} else {
			StdOut.println("Minimum number of moves = " + s.moves());
			for (Board board : s.solution()) {
				StdOut.println(board);
			}
		}
	}
}
