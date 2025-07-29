/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;


public class PointSET {

    private TreeSet<Point2D> points;

    public PointSET() {
        this.points = new TreeSet<Point2D>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return this.points.isEmpty();
    }                      // is the set empty?

    public int size() {
        return this.points.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        this.points.add(p);
    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return this.points.contains(p);
    }           // does the set contain point p?

    public void draw() {
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.setPenRadius(0.01);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D p : this.points) {
            p.draw();
        }
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Iterable<Point2D> iterator = new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                LinkedList<Point2D> iter = new LinkedList<Point2D>();
                for (Point2D p : points) {
                    if (rect.contains(p)) {
                        iter.add(p);
                    }
                }
                return iter.iterator();
            }
        };
        return iterator;
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        if (p == null) {
            throw new IllegalArgumentException("argument to nearest() is null");
        }
        double maxDist = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D point : points) {
            double dist = point.distanceSquaredTo(p);
            if (dist < maxDist) {
                maxDist = dist;
                nearest = point;
            }
        }
        return nearest;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
}
