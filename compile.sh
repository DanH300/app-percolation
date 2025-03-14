#!/bin/bash

# Crear directorio para archivos compilados
mkdir -p out

# Compilar las bibliotecas primero
javac -d out lib/*.java

# Compilar los archivos fuente
javac -d out -cp out src/*.java

echo "Compilación completada. Los archivos .class están en el directorio 'out'." 