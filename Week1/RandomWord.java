import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 1;
        String string = "";
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            boolean bool = StdRandom.bernoulli((double) 1 / i);
            if (bool) {
                string = item;
            }
            i++;
        }
        StdOut.println(string);
    }
}
