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

public class FastCollinearPoints {

    private final LineSegment[] segments;
    private final int numLines;


    // find all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
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

/*
        // No need to check last 3 unchecked points: len-3
        for (int p = 0; p < len - 3; p++) {
            Point pp = sortedPoints[p];
            int neiLen = len - 1 - p;
            Point[] neighbors = new Point[neiLen];
            // points has been sorted; exam all potential segments starting from point p, going through q,r,s
            System.arraycopy(sortedPoints, p + 1, neighbors, 0, neiLen);
            Arrays.sort(neighbors, pp.slopeOrder());
            // No need to check last 2 unchecked neighbors: neiLen-2
            for (int q = 0; q < neiLen - 2; q++) {
                double slope = pp.slopeTo(neighbors[q]);
                int cnt = 1;
                while (slope == pp.slopeTo(neighbors[q + 1])) {
                    cnt++;
                    q++;
                    if (q == neiLen - 1) break;
                }
                if (cnt >= 3) listLs.add(new LineSegment(pp, neighbors[q]));
            }
        } */
        for (int p = 0; p < len; p++) {
            Point pp = sortedPoints[p];
            int neiLen = len;
            Point[] neighbors = new Point[neiLen];

            System.arraycopy(sortedPoints, 0, neighbors, 0, neiLen);
            Arrays.sort(neighbors, pp.slopeOrder());
            for (int q = 1; q < neiLen - 2; q++) {
                LinkedList<Point> listPointsTemp = new LinkedList<>();

                Point pq = neighbors[q];
                Double slope = pp.slopeTo(pq);
                listPointsTemp.add(pq);
                while (q < neiLen - 1) {
                    Point pn = neighbors[q + 1];
                    if (Double.compare(slope, pp.slopeTo(pn)) == 0) {
                        listPointsTemp.add(pn);
                        q++;
                    }
                    else {
                        break;
                    }
                }
                if (listPointsTemp.size() >= 3) {
                    // Arrays.sort() is stable so no sort is needed for ListPointsTemp
                    if (pp.compareTo(listPointsTemp.get(0)) < 0) {
                        listLs.add(new LineSegment(pp, listPointsTemp.getLast()));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        if (collinear.segments == null) return;
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
