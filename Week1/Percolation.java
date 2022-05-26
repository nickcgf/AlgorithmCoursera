import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] isOpenArray;
    private int lengthOfN;
    private int openSits;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // idx0 used to be the top virtual root
        // idx n^2 + 1 used to be the bottom virtual root;
        this.uf = new WeightedQuickUnionUF(n*n+2);// n = (row-1)*N+col
        this.isOpenArray = new boolean[n*n+2];// n = (row-1)*N+col
        this.lengthOfN = n;
        this.openSits = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > this.lengthOfN || row<1 || col > lengthOfN || col < 1) return;// bad cases
        if (isOpen(row,col)) {
            // do nothing
        }else {
            this.isOpenArray[(row-1)*this.lengthOfN+col] = true;
            this.openSits++;
        }
        if (row == 1) {
            this.uf.union(col, 0);
        }else if (row == this.lengthOfN) {
            this.uf.union((row-1)*this.lengthOfN+col, row*row+1);
        }
        if (isOpen(row - 1, col)) {
            this.uf.union((row-2)*this.lengthOfN+col, (row-1)*this.lengthOfN+col);
        }
        if (isOpen(row+1, col)) {
            this.uf.union((row)*this.lengthOfN+col, (row-1)*this.lengthOfN+col);
        }
        if (isOpen(row, col-1)) {
            this.uf.union((row-1)*this.lengthOfN+col-1, (row-1)*this.lengthOfN+col);
        }
        if (isOpen(row, col+1)) {
            this.uf.union((row-1)*this.lengthOfN+col+1, (row-1)*this.lengthOfN+col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > this.lengthOfN || row<1 || col > lengthOfN || col < 1) {
            return false;// bad cases
        }
        return this.isOpenArray[(row-1)*this.lengthOfN+col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > this.lengthOfN || row<1 || col > lengthOfN ||col<1) return true;
        return this.uf.find((row-1)*this.lengthOfN+col) == this.uf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSits;
    }

    // does the system percolate?
    public boolean percolates() {
//        return this.uf.connected(0,this.lengthOfN^2+1);
        int p = this.uf.find(0);
        int q = this.uf.find(this.lengthOfN*this.lengthOfN+1);
        return p == q;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
