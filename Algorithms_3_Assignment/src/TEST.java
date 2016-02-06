import edu.princeton.cs.algs4.StdDraw;

public class TEST {

	public static void main(String[] args) {
		//Point[] points = {new Point(19000, 10000), new Point(18000, 10000), new Point(32000, 10000), new Point(21000, 10000), new Point(1234, 5678), new Point(14000, 10000)};
		Point[] points = {new Point(10000, 0), new Point(0, 10000), new Point(3000, 7000), new Point(7000, 3000), new Point(20000, 21000), new Point(3000, 4000), new Point(14000, 15000), new Point(6000, 7000)};
		//Point[] points = {new Point(4, 4),new Point(1, 1),new Point(3, 3),new Point(2, 2),new Point(5, 5),new Point(3, 1),new Point(3, 2),new Point(3, 6),new Point(3, 5),new Point(3, 4)};
		// draw the points
		StdDraw.show(0);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for(Point p : points){
			p.draw();
		}
		StdDraw.show();
		
		// print and draw the line segments
		FastCollinearPoints test = new FastCollinearPoints(points);
		for(LineSegment l : test.segments()){
			System.out.println(l);
			l.draw();
		}
	}
}
