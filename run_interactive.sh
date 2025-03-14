#!/bin/bash

# Check if a grid size was provided
if [ $# -eq 0 ]; then
    echo "Error: Missing grid size parameter!"
    echo "Usage: ./run_interactive.sh <grid_size>"
    echo "Recommended sizes:"
    echo "  - Small (5-10): Quick visualization"
    echo "  - Medium (20-50): Good balance"
    echo "  - Large (>50): Detailed but slower"
    exit 1
fi

# Check if the size is a number
if ! [[ "$1" =~ ^[0-9]+$ ]]; then
    echo "Error: Grid size must be a positive number!"
    exit 1
fi

# Check if the size is reasonable
if [ "$1" -gt 100 ]; then
    echo "Warning: Large grid size ($1) may run slowly!"
    echo "Press Enter to continue or Ctrl+C to cancel..."
    read
fi

# Run the interactive visualizer
echo "Starting interactive percolation with grid size $1..."
echo "Click on sites to open them. Close the window to exit."
java -cp out InteractivePercolationVisualizer $1 