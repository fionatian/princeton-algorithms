/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument to insert() is null");
        }
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return set.contains(p);

    }

    public void draw() {
        if (!set.isEmpty()) {
            for (Point2D p : set) {
                p.draw();
            }
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> listOfPoints = new LinkedList<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                listOfPoints.add(p);
            }
        }

        return listOfPoints;

    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (set.isEmpty()) return null;
        double minDis = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D q : set) {
            double qDis = p.distanceTo(q);
            if (qDis < minDis) {
                minDis = qDis;
                nearest = q;
            }
        }

        return nearest;
    }

    public static void main(String[] args) {

    }
}

