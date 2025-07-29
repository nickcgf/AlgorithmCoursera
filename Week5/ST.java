import edu.princeton.cs.algs4.Out;
import org.w3c.dom.Node;

public class ST <Key extends Comparable<Key>, Value>{

    ST() {

    }

    public void put(Key key, Value value) {
    }
    public Value get(Key key) {
        return null;
    }
    public void delete(Key key) {
        put(key, null);
    }
    boolean contains(Key key) {
        return get(key) != null;
    }
    boolean isEmpty() {
        this.getClass();
        return false;
    }
    int size() {
        return 0;
    }
    Iterable<Key> keys() {
        return null;
    }

    private class Node {
        Node left;
        Node right;
        Key key;
    }

    boolean check(Node node) {
        if (node == null) return true;
        if (node.left != null && node.key.compareTo(node.left.key) > 0) {
            return false;
        }

        if (node.right != null && node.key.compareTo(node.right.key) > 0) {
            return false;
        }
        return check(node.left) && check(node.right);
    }


    public static void main(String[] args) {
        double a = Double.NaN, b = Double.NaN;
        Double x = Double.valueOf(a);
        Double y = Double.valueOf(b);
        System.out.println(a==b);
        System.out.println(x.equals(y));
//        System.out.println(a==y);
    }

}
