package dsa;

/**
 * My implementation of the Union-Find data structure with weighting and path compression.
 * Based on the algorithm from Sedgewick & Wayne, but with some personal tweaks.
 */
public class WeightedQuickUnionUF {
    // Tried using ArrayLists but arrays are more efficient for this
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of elements in subtree rooted at i
    private int count;      // number of components - decreases as we union

    /**
     * Creates a new Union-Find data structure with n elements.
     * Initially, each element is in its own set (n sets total).
     */
    public WeightedQuickUnionUF(int n) {
        // Initialize the data structure
        count = n;
        parent = new int[n];
        size = new int[n];
        
        // Start with each element in its own set
        // Could use a fancy stream here, but this is clearer
        for (int i = 0; i < n; i++) {
            parent[i] = i;  // each element is its own root
            size[i] = 1;    // each tree has size 1
        }
    }

    /**
     * Returns the number of disjoint sets.
     */
    public int count() {
        return count;
    }

    /**
     * Finds the root of the set containing element p.
     * Uses path compression to flatten the tree.
     */
    public int find(int p) {
        validate(p);
        
        // Follow parent pointers until we reach a root
        // A root is an element that is its own parent
        while (p != parent[p]) {
            // Path compression - make every other node point to its grandparent
            // This keeps trees almost flat without much extra work
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    // Makes sure p is a valid index
    // Added more descriptive error message
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException(
                "Index " + p + " is out of bounds (should be between 0 and " + (n-1) + ")");
        }
    }

    /**
     * Checks if elements p and q are in the same set.
     */
    public boolean connected(int p, int q) {
        // Two elements are connected if they have the same root
        return find(p) == find(q);
    }

    /**
     * Merges the sets containing elements p and q.
     */
    public void union(int p, int q) {
        // Find the roots of the sets
        int rootP = find(p);
        int rootQ = find(q);
        
        // If p and q are already in the same set, nothing to do
        if (rootP == rootQ) return;

        // Make smaller root point to larger one
        // This keeps the trees balanced and operations efficient
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        
        // One less component after union
        count--;
    }
    
    // Could add a toString method for debugging
    /*
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Components: ").append(count).append("\n");
        for (int i = 0; i < parent.length; i++) {
            sb.append(i).append(" -> ").append(parent[i]).append("\n");
        }
        return sb.toString();
    }
    */
} 