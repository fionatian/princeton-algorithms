/* *****************************************************************************
 *  Name: Yuan Tian
 *  Date: 2019/7/13
 *  Description: PercolationStats
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double[] thresholds;
    private final int numTrials;
    private double meanVal;
    private double stddevVal;
    private boolean stddevValAssigned;
    private boolean meanValAssigned;


    public PercolationStats(int n, int trials) {
        numTrials = trials;
        thresholds = new double[numTrials];
        final int numSites = n * n;


        for (int t = 0; t < numTrials; t++) {
            Percolation perc = new Percolation(n);
            int[] openSeq = StdRandom.permutation(numSites);
            int count = 0;

            for (int p : openSeq) {
                int i = p / n + 1;
                int j = p % n + 1;
                count++;

                perc.open(i, j);
                if (perc.percolates()) {
                    thresholds[t] = (double) count / numSites;
                    // System.out.println("threshold " + t + " is " + thresholds[t]);
                    break;
                }
            }
        }

        stddevValAssigned = false;
        meanValAssigned = false;

    }

    public double mean() {
        if (!meanValAssigned) {
            meanVal = StdStats.mean(thresholds);
            meanValAssigned = true;
        }
        return meanVal;
    }

    public double stddev() {
        if (!stddevValAssigned) {
            stddevVal = StdStats.stddev(thresholds);
            stddevValAssigned = true;
        }
        return stddevVal;
    }

    public double confidenceLo() {

        double conLoVal = 0;

        conLoVal = mean() - CONFIDENCE_95 * stddev() / Math.sqrt((double) numTrials);

        return conLoVal;

    }

    public double confidenceHi() {
        double conHiVal = 0;

        conHiVal = mean() + CONFIDENCE_95 * stddev() / Math.sqrt(numTrials);
        return conHiVal;

    }

    public static void main(String[] args) {

        // n-by-n percolation system (read from command-line, default = 10)
        // T trials (read from command-line, default = 100)
        int n = 10;
        int t = 100;

        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
            if (t <= 0 || n <= 0) {
                throw new IllegalArgumentException(
                        "Illegal arguments! Both n and t need to be positive.");
            }
        }
        else {
            throw new IllegalArgumentException(
                    "Please specify two command-line arguments n and T and it will performs T independent computational experiments on an n-by-n grid.");
        }
        Stopwatch watch = new Stopwatch();
        PercolationStats percStats = new PercolationStats(n, t);
        double duration = watch.elapsedTime();


        System.out.println("mean                     = " + percStats.mean());
        System.out.println("stddev                   = " + percStats.stddev());
        System.out.println(
                "95%% confidence interval = [" + percStats.confidenceLo() + ", " + percStats
                        .confidenceHi() + "]");

        // System.out.println("Running time for gird " + n + "-by-" + n + " with " + T + " experiment trials is " + duration + " seconds");

    }
}
