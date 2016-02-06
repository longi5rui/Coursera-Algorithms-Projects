import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] segments;

	public BruteCollinearPoints(Point[] points) {
		if (checkNull(points))
			throw new java.lang.NullPointerException("Some arguments are null!");
		if (checkDuplicate(points))
			throw new java.lang.IllegalArgumentException("There exits repeated points!");
		int N = points.length;
		Arrays.sort(points);
		ArrayList<LineSegment> findSegment = new ArrayList<LineSegment>();

		for (int i = 0; i < N - 3; i++) {
			for (int j = i + 1; j < N - 2; j++) {
				for (int k = j + 1; k < N - 1; k++) {
					if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) {
						continue;
					} else {
						for (int m = k + 1; m < N; m++) {
							if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[m]))
								findSegment.add(new LineSegment(points[i], points[m]));
						}
					}
				}
			}
		}
		segments = findSegment.toArray(new LineSegment[findSegment.size()]);
	}

	public int numberOfSegments() {
		return segments.length;
	}

	public LineSegment[] segments() {
		return segments;
	}

	private boolean checkDuplicate(Point[] points) {
		boolean hasDup = false;
		Arrays.sort(points);
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].compareTo(points[i + 1]) == 0) {
				hasDup = true;
				break;
			}
		}
		return hasDup;
	}

	private boolean checkNull(Point[] points) {
		if (points == null)
			return true;
		for (Point p : points) {
			if (p == null)
				return true;
		}
		return false;
	}
}
