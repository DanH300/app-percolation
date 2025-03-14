#!/bin/bash

# Check if an input file was provided
if [ $# -eq 0 ]; then
    echo "Usage: ./run_percolation.sh <input_file>"
    echo "Example: ./run_percolation.sh data/input10.txt"
    exit 1
fi

# Run the percolation visualizer with the provided input file
java -cp out PercolationVisualizer $1 