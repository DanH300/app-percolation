#!/bin/bash

# Check if an input file was provided
if [ $# -eq 0 ]; then
    echo "Error: Missing input file!"
    echo "Usage: ./run_percolation.sh <input_file>"
    echo "Example: ./run_percolation.sh data/input10.txt"
    exit 1
fi

# Check if the file exists
if [ ! -f "$1" ]; then
    echo "Error: File '$1' not found!"
    echo "Available data files:"
    ls -1 data/*.txt
    exit 1
fi

# Run the percolation visualizer
echo "Starting visualization for $1..."
java -cp out PercolationVisualizer $1

# Note: visualization window will remain open until closed manually 