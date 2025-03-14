# Percolation Simulation Project

This project simulates percolation systems and analyzes the percolation threshold through Monte Carlo simulation. I built it as part of my exploration of algorithms and data structures, with a focus on the Union-Find algorithm.

# Author's
- José Andres De Lancer
- Paul De Lancer

## Requirements

Percolation is a physical process where a fluid flows through a porous material. Think of water seeping through coffee grounds or oil through rock. In our model:
- We have an n×n grid of sites
- Each site can be either open (fluid can flow) or blocked
- The system "percolates" when fluid can flow from the top row to the bottom row

## My Implementation

I've implemented this simulation using:
- Weighted Quick Union-Find with path compression for efficient connectivity checks
- Monte Carlo simulation to estimate the percolation threshold
- Interactive and file-based visualizers to see the process in action

One of the trickiest parts was handling the "backwash" problem, where sites connected to the bottom could incorrectly appear as full. I solved this by using two separate Union-Find structures.

## Getting Started

### Requirements
- Java 8 or higher

### Compilation

To compile the project, run:

```
./compile.sh
```

This will compile all Java files and place the .class files in the `out`