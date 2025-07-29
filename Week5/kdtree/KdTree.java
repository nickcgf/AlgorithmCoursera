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

public class KdTree {
    private enum Dimention {
        X, Y;

        public Dimention nextDimention() {
            if (this == Dimention.X) return Dimention.Y;
            if (this == Dimention.Y) return Dimention.X;
            return Dimention.X;
        }
    }

    private class KdNode {
        private Dimention dimention;
        private Point2D point;
        private KdNode left;
        private KdNode right;

        KdNode(Dimention dimention, Point2D point) {
            this.dimention = dimention;
            this.point = point;
            this.left = right = null;
        }

        Point2D findNearestNeighbour(Point2D point) {

            if (point == null) throw new IllegalArgumentException();
            double distanceSquared = this.point.distanceSquaredTo(point);
            Point2D subPoint = this.point;
            KdNode next = null;
            if (dimention == Dimention.X) {
                if (point.x() >= this.point.x() && this.right != null) {
                    next = this.right;
                }
                else if (point.x() < this.point.x() && this.left != null) {
                    next = this.left;
                }
            }
            else {
                if (point.y() >= this.point.y() && this.right != null) {
                    next = this.right;
                }
                else if (point.y() < this.point.y() && this.left != null) {
                    next = this.left;
                }
            }
            KdNode other = next == this.left ? this.right : this.left;
            if (next != null) {
                var temp = next.findNearestNeighbour(point);
                if (temp != null && temp.distanceSquaredTo(point) <= distanceSquared) {
                    subPoint = temp;
                    distanceSquared = temp.distanceSquaredTo(point);
                }
            }

            if (dimention == Dimention.X && distanceSquared <= Math.pow(
                    point.x() - this.point.x(), 2)) {
                return subPoint;
            }
            else if (dimention == Dimention.Y && distanceSquared <= Math.pow(
                    point.y() - this.point.y(), 2)) {
                return subPoint;
            }
            if (other == null) return subPoint;
            Point2D otherPoint = other.point;
            var temp = other.findNearestNeighbour(point);
            if (temp != null && temp.distanceSquaredTo(point) <= otherPoint.distanceSquaredTo(
                    point)) {
                otherPoint = temp;
            }
            return subPoint.distanceSquaredTo(point) <= otherPoint.distanceSquaredTo(point) ?
                   subPoint : otherPoint;
        }

    }

    private int size = 0;
    private KdNode root;

    public KdTree() {
        root = null;
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return size() == 0;
    }                      // is the set empty?

    public int size() {
        return size;
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        boolean inserted = false;
        if (isEmpty()) {
            root = new KdNode(Dimention.X, p);
            inserted = true;
        }
        else {
            KdNode currentNode = root;
            while (currentNode != null && !currentNode.point.equals(p)) {
                // System.out.println("Current node is " + currentNode.point);
                int cmpResult = 0;
                if (currentNode.dimention == Dimention.X) {
                    cmpResult = Point2D.X_ORDER.compare(currentNode.point, p);
                }
                else {
                    cmpResult = Point2D.Y_ORDER.compare(currentNode.point, p);
                }
                if (cmpResult > 0) {
                    if (currentNode.left == null) {
                        currentNode.left = new KdNode(currentNode.dimention.nextDimention(), p);
                        inserted = true;
                        break;
                    }
                    else {
                        currentNode = currentNode.left;
                    }
                }
                else if (cmpResult <= 0) {
                    if (currentNode.right == null) {
                        currentNode.right = new KdNode(currentNode.dimention.nextDimention(), p);
                        inserted = true;
                        break;
                    }
                    else {
                        currentNode = currentNode.right;
                    }
                }
                else {
                    break;
                }
            }
        }
        if (inserted) {
            size++;
        }
    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {

        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            return false;
        }

        KdNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.point.equals(p)) {
                return true;
            }
            int cmpResult = 0;
            if (currentNode.dimention == Dimention.X) {
                cmpResult = Point2D.X_ORDER.compare(currentNode.point, p);
            }
            else {
                cmpResult = Point2D.Y_ORDER.compare(currentNode.point, p);
            }

            if (cmpResult > 0) {
                currentNode = currentNode.left;
            }
            else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }           // does the set contain point p?

    public void draw() {
        if (isEmpty()) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        LinkedList<KdNode> queue = new LinkedList<KdNode>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            KdNode node = queue.poll();
            node.point.draw();
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<KdNode> queue = new LinkedList<KdNode>();
        LinkedList<Point2D> nodes = new LinkedList<Point2D>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            KdNode node = queue.poll();
            if (rect.contains(node.point)) {
                nodes.add(node.point);
            }

            if (node.dimention == Dimention.X) {
                if (rect.xmin() < node.point.x() && node.left != null) {
                    // check add left;
                    queue.add(node.left);
                }
                if (rect.xmax() >= node.point.x() && node.right != null) {
                    queue.add(node.right);
                }
            }

            if (node.dimention == Dimention.Y) {
                if (rect.ymin() < node.point.y() && node.left != null) {
                    queue.add(node.left);
                }
                if (rect.ymax() >= node.point.y() && node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                return nodes.iterator();
            }
        };
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return root.findNearestNeighbour(p);
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
}
