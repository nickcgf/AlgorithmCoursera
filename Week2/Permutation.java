import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            queue.enqueue(input);
        }
        Iterator<String> iterator = queue.iterator();
        int idx = 0;
        while (iterator.hasNext()&&(idx<k)) {
            StdOut.println(iterator.next());
            idx += 1;
        }
    }
}