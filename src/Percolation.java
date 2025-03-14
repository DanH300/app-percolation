import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

public class Percolation {
    private boolean[][] grid;          // grid[i][j] = true si el sitio (i, j) está abierto
    private int n;                     // tamaño de la cuadrícula n x n
    private int openSites;             // número de sitios abiertos
    private WeightedQuickUnionUF uf;   // estructura de datos Union-Find para determinar percolación
    private WeightedQuickUnionUF ufFull; // estructura de datos Union-Find para determinar si un sitio está lleno

    // Constructs an n x n percolation system, with all sites blocked.
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n debe ser mayor que 0");
        }
        
        this.n = n;
        this.openSites = 0;
        this.grid = new boolean[n][n];
        
        // Inicializar todos los sitios como bloqueados
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        
        // Crear estructura Union-Find con n*n sitios + 2 sitios virtuales (superior e inferior)
        uf = new WeightedQuickUnionUF(n * n + 2);
        
        // Crear estructura Union-Find con n*n sitios + 1 sitio virtual (superior)
        // Esta estructura se usa para determinar si un sitio está lleno
        ufFull = new WeightedQuickUnionUF(n * n + 1);
        
        // Sitio virtual superior es n*n, sitio virtual inferior es n*n+1
        int virtualTop = n * n;
        int virtualBottom = n * n + 1;
        
        // Conectar sitios de la primera fila al sitio virtual superior
        for (int j = 0; j < n; j++) {
            uf.union(encode(0, j), virtualTop);
            ufFull.union(encode(0, j), virtualTop);
        }
        
        // Conectar sitios de la última fila al sitio virtual inferior
        for (int j = 0; j < n; j++) {
            uf.union(encode(n - 1, j), virtualBottom);
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        validate(i, j);
        
        if (isOpen(i, j)) {
            return;  // El sitio ya está abierto
        }
        
        grid[i][j] = true;
        openSites++;
        
        // Conectar con sitios vecinos abiertos
        int[] dx = {-1, 1, 0, 0};  // arriba, abajo, izquierda, derecha
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
        
        // Un sitio está lleno si está conectado al sitio virtual superior
        return ufFull.connected(encode(i, j), n * n);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // El sistema percola si el sitio virtual superior está conectado al sitio virtual inferior
        return uf.connected(n * n, n * n + 1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return i * n + j;
    }
    
    // Valida que (i, j) sea una posición válida en la cuadrícula
    private void validate(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IllegalArgumentException("Índice fuera de rango: (" + i + ", " + j + ")");
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
