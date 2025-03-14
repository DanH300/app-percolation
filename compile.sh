#!/bin/bash

# Create directory for compiled files if it doesn't exist
# Had issues with missing directory before, so being explicit
if [ ! -d "out" ]; then
    mkdir -p out
    echo "Created output directory"
fi

# Compile the libraries first - these need to be available for the main classes
echo "Compiling library files..."
javac -d out lib/*.java

# Compile the source files with the classpath including the libraries
# Using -Xlint:none to suppress deprecation warnings from StdDraw
echo "Compiling source files..."
javac -Xlint:none -d out -cp out src/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful! The .class files are in the 'out' directory."
else
    echo "Compilation failed. Check for errors above."
    exit 1
fi

# TODO: Add option for cleaning old class files first 