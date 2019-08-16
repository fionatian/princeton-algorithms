
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

public class KdTree {

    static final double scale = 1;

    private Node root;
    private int size;
    private double minDistance;

    private class Node {
        private Point2D p;
        private RectHV rec;
        private boolean byX;
        private Node left, right;

        Node(Point2D p, RectHV rec, boolean byX) {
            this.p = p;
            this.rec = rec;
            this.byX = byX;
        }

    }

    public KdTree() {
        size = 0;

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("calls insert() with a null point");
        }
        if (!contains(p)) {
            root = insert(root, p, 0, 0, scale, scale, true);
            size++;
        }
    }

    private Node insert(Node n, Point2D point, double xmin, double ymin, double xmax, double ymax,
                        boolean order) {
        if (n == null) {
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax), order);

        }
        if (n.byX && Point2D.X_ORDER.compare(point, n.p) <= 0) {
            xmin = n.rec.xmin();
            ymin = n.rec.ymin();
            xmax = n.p.x();
            ymax = n.rec.ymax();
            n.left = insert(n.left, point, xmin, ymin, xmax, ymax, !n.byX);
        }
        else if (n.byX && Point2D.X_ORDER.compare(point, n.p) > 0) {
            xmin = n.p.x();
            ymin = n.rec.ymin();
            xmax = n.rec.xmax();
            ymax = n.rec.ymax();
            n.right = insert(n.right, point, xmin, ymin, xmax, ymax, !n.byX);
        }
        else if (!n.byX && Point2D.Y_ORDER.compare(point, n.p) <= 0) {
            xmin = n.rec.xmin();
            ymin = n.rec.ymin();
            xmax = n.rec.xmax();
            ymax = n.p.y();
            n.left = insert(n.left, point, xmin, ymin, xmax, ymax, !n.byX);
        }
        else if (!n.byX && Point2D.Y_ORDER.compare(point, n.p) > 0) {
            xmin = n.rec.xmin();
            ymin = n.p.y();
            xmax = n.rec.xmax();
            ymax = n.rec.ymax();
            n.right = insert(n.right, point, xmin, ymin, xmax, ymax, !n.byX);
        }
        return n;
    }


    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(p) != null;

    }

    public Node get(Point2D p) {
        return get(root, p);
    }

    private Node get(Node n, Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("calls get() with a null point");
        }
        if (n == null) return null;

        if (point.equals(n.p)) return n;

        if ((n.byX && Point2D.X_ORDER.compare(point, n.p) <= 0) || (!n.byX
                && Point2D.Y_ORDER.compare(point, n.p) <= 0)) {
            return get(n.left, point);
        }
        // else if ((n.byX && Point2D.X_ORDER.compare(point, n.p) > 0) || (!n.byX && Point2D.Y_ORDER.compare(point, n.p) > 0)) {
        else
            return get(n.right, point);


    }


    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            node.p.draw();
            if (node.byX) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(node.p.x(), node.rec.ymin(), node.p.x(), node.rec.ymax());
            }

            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(node.rec.xmin(), node.p.y(), node.rec.xmax(), node.p.y());
            }
            draw(node.left);
            draw(node.right);
        }
    }


    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> listOfPoints = new LinkedList<Point2D>();

        rangeSearch(root, rect, listOfPoints);

        return listOfPoints;

    }

    private void rangeSearch(Node node, RectHV target, LinkedList<Point2D> list) {
        if (node != null) {
            if (node.rec.intersects(target)) {
                if (target.contains(node.p)) list.add(node.p);
                rangeSearch(node.left, target, list);
                rangeSearch(node.right, target, list);
            }
        }

    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        minDistance = scale * scale * 2;
        Point2D nearest = nearestSearch(root, p, null);

        return nearest;
    }

    private Point2D nearestSearch(Node node, Point2D p, Point2D nearestFound) {
        double cur;
        if (node != null) {
            if (node.rec.distanceTo(p) < minDistance) {
                cur = p.distanceTo(node.p);
                if (cur < minDistance) {
                    minDistance = cur;
                    nearestFound = node.p;
                }
                if ((node.byX && node.p.x() < p.x()) || (!node.byX && node.p.y() < p.y())) {
                    nearestFound = nearestSearch(node.right, p, nearestFound);
                    nearestFound = nearestSearch(node.left, p, nearestFound);
                }
                else {
                    nearestFound = nearestSearch(node.left, p, nearestFound);
                    nearestFound = nearestSearch(node.right, p, nearestFound);
                }
            }
        }
        return nearestFound;
    }

    public static void main(String[] args) {

    }
}
