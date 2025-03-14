package dsa;

/**
 * Implementación de la estructura de datos Union-Find con ponderación y compresión de caminos.
 * Esta es una versión simplificada de la implementación de Princeton.
 */
public class WeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of elements in subtree rooted at i
    private int count;      // number of components

    /**
     * Inicializa una estructura union-find vacía con n elementos 0 a n-1.
     * Inicialmente, cada elemento está en su propio conjunto.
     */
    public WeightedQuickUnionUF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Devuelve el número de conjuntos.
     */
    public int count() {
        return count;
    }

    /**
     * Devuelve el componente identificador para el componente que contiene p.
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // compresión de camino
            p = parent[p];
        }
        return p;
    }

    // Valida que p sea un índice válido
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("índice " + p + " no está entre 0 y " + (n-1));
        }
    }

    /**
     * Devuelve true si los elementos p y q están en el mismo conjunto.
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Une el conjunto que contiene el elemento p con el conjunto que contiene el elemento q.
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // Hace que la raíz del árbol más pequeño apunte a la raíz del árbol más grande
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
} 