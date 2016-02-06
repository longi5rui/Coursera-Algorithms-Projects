import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;

public class PointSET {
	private SET<Point2D> set;

	public PointSET() {
		set = new SET<Point2D>();
	}

	public boolean isEmpty() {
		return set.size() == 0;
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new NullPointerException("Argument is null!");
		if (set.contains(p))
			return;
		else
			set.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerException("Argument is null!");
		return set.contains(p);
	}

	public void draw() {
		Iterator<Point2D> itr = set.iterator();
		while (itr.hasNext()) {
			Point2D point = itr.next();
			StdDraw.point(point.x(), point.y());
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException("Argument is null!");
		Queue<Point2D> queue = new Queue<Point2D>();
		for (Point2D p : set) {
			if (rect.contains(p))
				queue.enqueue(p);
		}
		return queue;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerException("Argument is null!");
		if (isEmpty())
			return null;
		double minDistance = Double.MAX_VALUE;
		Point2D temp = null;
		for (Point2D point : set) {
			if (point.distanceSquaredTo(p) < minDistance) {
				minDistance = point.distanceSquaredTo(p);
				temp = point;
			}
		}
		return temp;
	}

	public static void main(String[] args) {
		Point2D p1 = new Point2D(0.1, 0.1);
		Point2D p2 = new Point2D(0.9, 0.9);
		Point2D p3 = new Point2D(0.5, 0.5);
		Point2D p4 = new Point2D(0.9, 0.1);
		RectHV rect = new RectHV(0.51, 0.09, 0.91, 0.91);

		PointSET test = new PointSET();
		test.insert(p1);
		test.insert(p2);
		test.insert(p3);
		test.insert(p4);
		System.out.println(test.isEmpty());
		System.out.println(test.nearest(new Point2D(0.8, 0.2)));
		System.out.println();
		Iterable<Point2D> itr = test.range(rect);
		for (Point2D pt : itr)
			System.out.println(pt);
	}
}
