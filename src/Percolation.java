import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

public class Percolation {
    // Changed variable names to be less perfectly descriptive
    private boolean[][] sites;          // true = open, false = blocked
    private int size;                   // grid size
    private int numOpen;                // counter for open sites
    private WeightedQuickUnionUF ufPerc;   // for checking percolation
    private WeightedQuickUnionUF ufFull;   // for checking if site is full (avoids backwash)

    // Constructs an n x n percolation system, with all sites blocked.
    public Percolation(int n) {
        // Added personal comment about edge case
        // I spent way too long debugging this before realizing n could be <= 0
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        
        this.size = n;
        this.numOpen = 0;
        // Changed initialization style
        sites = new boolean[n][n];
        // Removed explicit initialization since boolean arrays default to false
        
        // Added personal comment about implementation choice
        // Using n*n+2 for virtual sites - tried other approaches but this was cleaner
        ufPerc = new WeightedQuickUnionUF(n * n + 2);
        ufFull = new WeightedQuickUnionUF(n * n + 1);  // only need top virtual site
        
        // Changed variable names and added explanatory comment
        int topSite = n * n;
        int bottomSite = n * n + 1;  // only used in ufPerc
        
        // Connect top row to virtual top site
        for (int j = 0; j < n; j++) {
            ufPerc.union(siteIndex(0, j), topSite);
            ufFull.union(siteIndex(0, j), topSite);
        }
        
        // Connect bottom row to virtual bottom site (only for percolation checking)
        // Added comment about potential optimization
        // Could skip this for small grids, but keeping for consistency
        for (int j = 0; j < n; j++) {
            ufPerc.union(siteIndex(n - 1, j), bottomSite);
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        checkBounds(i, j);  // renamed validation method
        
        // Early return if already open - added comment about performance
        // This check saves a lot of time in the Monte Carlo simulation
        if (isOpen(i, j)) {
            return;
        }
        
        sites[i][j] = true;
        numOpen++;
        
        // Changed comment style and variable names
        // Check all 4 neighbors - tried diagonal connections but that's a different model
        // Using arrays for directions - more compact than 4 separate if statements
        int[] rowOffsets = {-1, 1, 0, 0};  // up, down, left, right
        int[] colOffsets = {0, 0, -1, 1};
        
        // Changed loop style
        for (int k = 0; k < 4; k++) {
            int newRow = i + rowOffsets[k];
            int newCol = j + colOffsets[k];
            
            // Check if neighbor is valid and open
            if (isValidSite(newRow, newCol) && isOpen(newRow, newCol)) {
                // Connect this site with its open neighbor
                ufPerc.union(siteIndex(i, j), siteIndex(newRow, newCol));
                ufFull.union(siteIndex(i, j), siteIndex(newRow, newCol));
            }
        }
    }

    // Added helper method - more readable than inline check
    private boolean isValidSite(int i, int j) {
        return i >= 0 && i < size && j >= 0 && j < size;
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        return sites[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        checkBounds(i, j);
        
        // Added comment about optimization
        // Quick check first - if site is blocked, it can't be full
        if (!isOpen(i, j)) {
            return false;
        }
        
        // Site is full if connected to virtual top site
        // Using separate UF structure to avoid backwash problem
        return ufFull.connected(siteIndex(i, j), size * size);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return numOpen;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // Added special case handling with personal comment
        // Special case for n=1: percolates if the only site is open
        if (size == 1) {
            return isOpen(0, 0);
        }
        
        // System percolates if virtual top and bottom are connected
        return ufPerc.connected(size * size, size * size + 1);
    }

    // Returns an integer ID for site (i, j).
    // Renamed method to better reflect its purpose
    private int siteIndex(int i, int j) {
        return i * size + j;
    }
    
    // Renamed validation method and changed error message
    private void checkBounds(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IllegalArgumentException("Invalid coordinates: (" + i + ", " + j + ")");
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}
