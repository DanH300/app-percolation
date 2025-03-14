#!/bin/bash

# Check if a grid size was provided
if [ $# -eq 0 ]; then
    echo "Usage: ./run_interactive.sh <grid_size>"
    echo "Example: ./run_interactive.sh 10"
    exit 1
fi

# Run the interactive percolation visualizer with the provided grid size
java -cp out InteractivePercolationVisualizer $1 