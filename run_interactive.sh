#!/bin/bash

# Verificar si se proporcionó un tamaño de cuadrícula
if [ $# -eq 0 ]; then
    echo "Uso: ./run_interactive.sh <tamaño_de_cuadrícula>"
    echo "Ejemplo: ./run_interactive.sh 10"
    exit 1
fi

# Ejecutar el visualizador interactivo de percolación con el tamaño de cuadrícula proporcionado
java -cp out InteractivePercolationVisualizer $1 