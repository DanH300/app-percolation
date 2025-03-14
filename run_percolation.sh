#!/bin/bash

# Verificar si se proporcionó un archivo de entrada
if [ $# -eq 0 ]; then
    echo "Uso: ./run_percolation.sh <archivo_de_entrada>"
    echo "Ejemplo: ./run_percolation.sh data/input10.txt"
    exit 1
fi

# Ejecutar el visualizador de percolación con el archivo de entrada proporcionado
java -cp out PercolationVisualizer $1 