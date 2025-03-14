import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

public class Percolation {
    private boolean[][] grid;          // grid[i][j] = true if site (i, j) is open
    private int n;                     // size of the n x n grid
    private int openSites;             // number of open sites
    private WeightedQuickUnionUF uf;   // Union-Find data structure for percolation detection
    private WeightedQuickUnionUF ufFull; // Union-Find data structure to determine if a site is full

    // Constructs an n x n percolation system, with all sites blocked.
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        
        this.n = n;
        this.openSites = 0;
        this.grid = new boolean[n][n];
        
        // Initialize all sites as blocked
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        
        // Create Union-Find structure with n*n sites + 2 virtual sites (top and bottom)
        uf = new WeightedQuickUnionUF(n * n + 2);
        
        // Create Union-Find structure with n*n sites + 1 virtual site (top)
        // This structure is used to determine if a site is full
        ufFull = new WeightedQuickUnionUF(n * n + 1);
        
        // Virtual top site is n*n, virtual bottom site is n*n+1
        int virtualTop = n * n;
        int virtualBottom = n * n + 1;
        
        // Connect sites in the first row to the virtual top site
        for (int j = 0; j < n; j++) {
            uf.union(encode(0, j), virtualTop);
            ufFull.union(encode(0, j), virtualTop);
        }
        
        // Connect sites in the last row to the virtual bottom site
        for (int j = 0; j < n; j++) {
            uf.union(encode(n - 1, j), virtualBottom);
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        validate(i, j);
        
        if (isOpen(i, j)) {
            return;  // Site is already open
        }
        
        grid[i][j] = true;
        openSites++;
        
        // Connect with open neighboring sites
        int[] dx = {-1, 1, 0, 0};  // up, down, left, right
        int[] dy = {0, 0, -1, 1};
        
        for (int k = 0; k < 4; k++) {
            int ni = i + dx[k];
            int nj = j + dy[k];
            
            if (ni >= 0 && ni < n && nj >= 0 && nj < n && isOpen(ni, nj)) {
                uf.union(encode(i, j), encode(ni, nj));
                ufFull.union(encode(i, j), encode(ni, nj));
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return grid[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        validate(i, j);
        
        if (!isOpen(i, j)) {
            return false;
        }
        
        // A site is full if it's connected to the virtual top site
        return ufFull.connected(encode(i, j), n * n);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // The system percolates if the virtual top site is connected to the virtual bottom site
        return uf.connected(n * n, n * n + 1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return i * n + j;
    }
    
    // Validates that (i, j) is a valid position in the grid
    private void validate(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IllegalArgumentException("Index out of range: (" + i + ", " + j + ")");
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
