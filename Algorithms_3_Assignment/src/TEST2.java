import java.util.Arrays;
public class TEST2 {

	public static void main(String[] args) {
		Point v = new Point(1,1);
		Point[] points = {new Point(4, 4),new Point(3, 3),new Point(1, 1),new Point(2, 2)};
		//Arrays.sort(points, v.slopeOrder());
		Arrays.sort(points,1,4);
		for(Point p : points)
			System.out.println(p);
	}

}
