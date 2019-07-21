/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private final int numLines;


    // find all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null Points are provided!");

        // Check if there is any null points
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("Null Points are provided@");
        }

        // Don't mutate the points[] array
        Point[] sortedPoints = points.clone();

        // check if there is any duplicated points
        Arrays.sort(sortedPoints);
        final int len = sortedPoints.length;

        for (int i = 1; i < len; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0) {
                throw new IllegalArgumentException(
                        "Duplicated points are provided: " + sortedPoints[i]);
            }
        }


        if (len < 4) {
            numLines = 0;
            segments = new LineSegment[0];
            return;
        }

        List<LineSegment> listLs = new LinkedList<>();

        for (int p = 0; p < len; p++) {
            for (int q = p + 1; q < len; q++) {
                for (int r = q + 1; r < len; r++) {
                    Point pp = sortedPoints[p];
                    Point pq = sortedPoints[q];
                    Point pr = sortedPoints[r];
                    if (Double.compare(pp.slopeTo(pq), pp.slopeTo(pr)) == 0) {
                        double slope = pp.slopeTo(pq);
                        for (int s = r + 1; s < len; s++) {
                            Point ps = sortedPoints[s];
                            if (slope == pp.slopeTo(ps)) {
                                listLs.add(new LineSegment(pp, ps));
                            }

                        }
                    }

                }
            }
        }
        segments = listLs.toArray(new LineSegment[0]);
        numLines = segments.length;

    }


    public int numberOfSegments() {
        return numLines;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        if (collinear.segments == null) return;
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
