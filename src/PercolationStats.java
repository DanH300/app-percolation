import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    private double[] thresholds;  // Almacena los umbrales de percolación para cada experimento
    private int m;                // Número de experimentos
    private static final double CONFIDENCE_95 = 1.96;  // Constante para el intervalo de confianza del 95%

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("n y m deben ser mayores que 0");
        }
        
        this.m = m;
        thresholds = new double[m];
        
        for (int i = 0; i < m; i++) {
            Percolation perc = new Percolation(n);
            
            // Abrir sitios aleatoriamente hasta que el sistema percole
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            
            // Calcular el umbral de percolación para este experimento
            thresholds[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(m));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(m));
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
