// Accepts filename (String) as command-line argument; and visually reports if the percolation system whose size and
// open sites are specified by the file percolates or not.

import java.awt.Font;

import stdlib.In;
import stdlib.StdDraw;

public class PercolationVisualizer {
    // Delay in milliseconds (controls animation speed).
    private static final int DELAY = 100;

    // Entry point.
    public static void main(String[] args) {
        String filename = args[0];
        simulateFromFile(filename);
    }

    // Draws n-by-n percolation system.
    public static void draw(Percolation perc, int n) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05 * n, 1.05 * n);
        StdDraw.setYscale(-0.05 * n, 1.05 * n);
        StdDraw.filledSquare(n / 2.0, n / 2.0, n / 2.0);
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                } else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                } else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                StdDraw.filledSquare(col + 0.5, n - row - 0.5, 0.45);
            }
        }
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.25 * n, -n * 0.025, perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdDraw.text(0.75 * n, -n * 0.025, "percolates");
        } else {
            StdDraw.text(0.75 * n, -n * 0.025, "does not percolate");
        }
    }

    // Simulates visualization from a data file
    private static void simulateFromFile(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        Percolation perc = new Percolation(n);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        StdDraw.pause(0);
        draw(perc, n);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        StdDraw.pause(DELAY);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            draw(perc, n);
            StdDraw.enableDoubleBuffering();
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }
}
