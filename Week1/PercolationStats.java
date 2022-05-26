import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.Math;

public class PercolationStats {
    private final double CONFIDENCE_95 = 1.96;
    private int trials;
    private double[] counts;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.counts = new double[trials];// record every trial's open sites;
        for (int i = 0; i < this.trials; i++) {
            Percolation percolation = new Percolation(n);
            int temp = n*n;//
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                percolation.open(row,col);
            }
            this.counts[i] = percolation.numberOfOpenSites()/Double.valueOf(temp);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.counts);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.counts);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean()-CONFIDENCE_95*this.stddev()/Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean()+CONFIDENCE_95*this.stddev()/Math.sqrt(this.trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n,trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + "["+stats.confidenceLo()+","+stats.confidenceHi()+"]");
    }
}
