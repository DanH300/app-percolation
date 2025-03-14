#!/bin/bash

# Create directory for compiled files
mkdir -p out

# Compile the libraries first
javac -d out lib/*.java

# Compile the source files
javac -d out -cp out src/*.java

echo "Compilation completed. The .class files are in the 'out' directory." 