import java.util.Arrays;

public class UF {
    private int[] idx;

    public void UF(int n) {
        this.idx = new int[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
    }

    public static void main(String[] args) {

    }
}

