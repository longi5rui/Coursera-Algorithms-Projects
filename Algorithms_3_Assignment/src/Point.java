import java.util.Comparator;
import edu.princeton.cs.algs4.*;

public class Point implements Comparable<Point> {
	private final int x;
	private final int y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	public double slopeTo(Point that) {
		int dx = this.x - that.x;
		int dy = this.y - that.y;
		if(dx == 0 && dy == 0)			return Double.NEGATIVE_INFINITY;
		if(dx == 0)						return Double.POSITIVE_INFINITY;
		if(dy == 0)						return +0.0;
		return (double)dy / dx;
	}

	public int compareTo(Point that) {
		if(this.y < that.y || (this.y == that.y && this.x < that.x))
			return -1;
		if(this.x == that.x && this.y == that.y)
			return 0;
		return +1;	
	}

	public Comparator<Point> slopeOrder() {
		return new SlopeOrder();
	}
	
	private class SlopeOrder implements Comparator<Point> {
		public int compare(Point p, Point q) {
			if(slopeTo(p) < slopeTo(q))			return -1;
			else if(slopeTo(p) == slopeTo(q))	return 0;
			else return 1;				
		}
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
