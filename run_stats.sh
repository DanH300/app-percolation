#!/bin/bash

# Verificar si se proporcionaron los parámetros necesarios
if [ $# -lt 2 ]; then
    echo "Uso: ./run_stats.sh <tamaño_de_cuadrícula> <número_de_experimentos>"
    echo "Ejemplo: ./run_stats.sh 20 100"
    exit 1
fi

# Ejecutar las estadísticas de percolación con los parámetros proporcionados
java -cp out PercolationStats $1 $2 