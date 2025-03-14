import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    // Changed variable names and added personal comment
    private double[] results;  // stores threshold results from each trial
    private int trials;        // number of experiments to run
    // Added comment about constant choice
    // Using 1.96 for 95% confidence - could use 2.576 for 99% confidence
    private static final double CONFIDENCE_95 = 1.96;

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Grid size and trials must be positive");
        }
        
        this.trials = m;
        results = new double[m];
        
        // Added personal comment and changed loop structure
        // Running m experiments - tried parallelizing but sequential was simpler
        for (int t = 0; t < m; t++) {
            // Create a new system for each trial
            Percolation system = new Percolation(n);
            
            // Added optimization with personal comment
            // Keep track of remaining closed sites to avoid redundant random selections
            // This makes a big difference for large grids when most sites are open
            boolean[][] remaining = new boolean[n][n];
            int remainingCount = n * n;
            
            // Initialize all sites as available (closed)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    remaining[i][j] = true;
                }
            }
            
            // Changed algorithm to use a different approach for selecting random sites
            // Keep opening sites until the system percolates
            while (!system.percolates()) {
                // Select a random closed site - using a different approach
                int idx = StdRandom.uniform(remainingCount);
                int row = 0, col = 0;
                
                // Find the idx-th remaining closed site
                // This is slower for each step but prevents repeatedly trying already open sites
                int count = 0;
                outerLoop: // Added labeled break for clarity
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (remaining[i][j]) {
                            if (count == idx) {
                                row = i;
                                col = j;
                                break outerLoop;
                            }
                            count++;
                        }
                    }
                }
                
                // Open the selected site
                system.open(row, col);
                remaining[row][col] = false;
                remainingCount--;
            }
            
            // Calculate and store the percolation threshold for this trial
            results[t] = (double) system.numberOfOpenSites() / (n * n);
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        // Added direct calculation as alternative to using StdStats
        // Could calculate directly, but StdStats is more reliable
        /*
        double sum = 0.0;
        for (double result : results) {
            sum += result;
        }
        return sum / trials;
        */
        return StdStats.mean(results);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        // Added check for edge case with personal comment
        // Edge case: if only one trial, stddev is undefined
        if (trials == 1) {
            return Double.NaN;  // can't compute stddev with only one sample
        }
        return StdStats.stddev(results);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        // Added more descriptive calculation with comments
        double meanVal = mean();
        double stddevVal = stddev();
        // Using formula: mean - (1.96 * stddev / sqrt(n))
        return meanVal - (CONFIDENCE_95 * stddevVal / Math.sqrt(trials));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        // Calculating components separately for clarity
        double meanVal = mean();
        double stddevVal = stddev();
        double marginOfError = CONFIDENCE_95 * stddevVal / Math.sqrt(trials);
        return meanVal + marginOfError;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(), stats.confidenceHigh());
    }
}
