# Percolation Project

This project implements a percolation model, which is a physical phenomenon where a fluid can flow through a porous material. In computational terms, a system is modeled as an n×n grid of sites, where each site is either open (can allow fluid to pass) or blocked (cannot allow fluid to pass). A system "percolates" if there is a path of open sites from the top to the bottom.

# Author's
- José Andres De Lancer
- Paul De Lancer

## Requirements

- Java 8 or higher

## Compilation

To compile the project, run the following command:

```
./compile.sh
```

This will compile all Java files and place the .class files in the `out` directory.

## Execution

The project provides three different ways to run the percolation simulation:

### 1. Percolation Visualizer

This visualizer shows a simulation based on a data file. To run it:

```
./run_percolation.sh data/input10.txt
```

You can replace `input10.txt` with any other data file in the `data/` directory.

### 2. Interactive Visualizer

This visualizer allows you to interact with the system by clicking to open sites. To run it:

```
./run_interactive.sh 10
```

The parameter `10` specifies the grid size (10×10 in this case).

### 3. Percolation Statistics

This program performs multiple experiments to estimate the percolation threshold. To run it:

```
./run_stats.sh 20 100
```

The first parameter (`20`) specifies the grid size, and the second parameter (`100`) specifies the number of experiments to perform.

## Data Files

The `data/` directory contains various data files to test the percolation system. Each file has the format:

1. First line: size n of the n×n system
2. Subsequent lines: pairs of coordinates (i, j) representing sites to open

## Project Structure

- `src/`: Contains the Java source code files
  - `Percolation.java`: Implements the percolation model
  - `PercolationStats.java`: Performs statistical analysis on the percolation threshold
  - `PercolationVisualizer.java`: Provides graphical visualization of the percolation system
  - `InteractivePercolationVisualizer.java`: Allows interaction with the percolation system
- `lib/`: Contains the required libraries
- `data/`: Contains test data files
- `out/`: Contains compiled files 