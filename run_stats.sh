#!/bin/bash

# Check if the required parameters were provided
if [ $# -lt 2 ]; then
    echo "Usage: ./run_stats.sh <grid_size> <number_of_experiments>"
    echo "Example: ./run_stats.sh 20 100"
    exit 1
fi

# Run the percolation statistics with the provided parameters
java -cp out PercolationStats $1 $2 