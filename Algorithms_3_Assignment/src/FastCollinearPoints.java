import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private LineSegment[] segments;

	public FastCollinearPoints(Point[] points) {
		if (checkNull(points))
			throw new java.lang.NullPointerException("Some arguments are null!");
		if (checkDuplicate(points))
			throw new java.lang.IllegalArgumentException("There exits repeated points!");

		Arrays.sort(points);

		Point[] aux = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			aux[i] = points[i];
		}
		ArrayList<LineSegment> findSegment = new ArrayList<LineSegment>();
		for (Point p : points) {
			Arrays.sort(aux, p.slopeOrder());
			for (int i = 0; i < aux.length;) {
				int count = 0;
				while (i + count + 1 <= (aux.length - 1) && p.slopeTo(aux[i]) == p.slopeTo(aux[i + count + 1]))
					count++;
				if (count >= 2) {
					Arrays.sort(aux, i, i + count + 1);
					if (p.compareTo(aux[i]) < 0) {
						LineSegment newline = new LineSegment(p, aux[i + count]);
						if (!ifDuplicate(findSegment, newline))
							findSegment.add(newline);
					} else {
						if (p.compareTo(aux[i + count]) < 0) {
							LineSegment newline = new LineSegment(aux[i], aux[i + count]);
							if (!ifDuplicate(findSegment, newline))
								findSegment.add(newline);
						} else {
							LineSegment newline = new LineSegment(aux[i], p);
							if (!ifDuplicate(findSegment, newline))
								findSegment.add(newline);
						}
					}
				}
				i += count + 1;
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

	private boolean ifDuplicate(ArrayList<LineSegment> a, LineSegment line) {
		for (LineSegment l : a) {
			if (l.toString().equals(line.toString())) {
				return true;
			}
		}
		return false;
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
