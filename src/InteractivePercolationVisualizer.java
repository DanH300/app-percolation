// Accepts n (int) as command-line argument; constructs an n-by-n percolation system; and allows you to interactively
// open sites in the system by clicking on them and visually inspect if the system percolates or not.

import stdlib.StdDraw;
import stdlib.StdOut;

public class InteractivePercolationVisualizer {
    // Delay in milliseconds (controls animation speed).
    private static final int DELAY = 20;

    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        StdDraw.pause(0);
        StdOut.println(n);
        Percolation perc = new Percolation(n);
        PercolationVisualizer.draw(perc, n);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        StdDraw.pause(DELAY);
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                int i = (int) (n - Math.floor(y) - 1);
                int j = (int) (Math.floor(x));
                if (i >= 0 && i < n && j >= 0 && j < n) {
                    if (!perc.isOpen(i, j)) {
                        StdOut.println(i + " " + j);
                    }
                    perc.open(i, j);
                }
                PercolationVisualizer.draw(perc, n);
            }
            StdDraw.enableDoubleBuffering();
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }
}
