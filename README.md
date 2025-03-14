# Proyecto de Percolación

Este proyecto implementa un modelo de percolación, que es un fenómeno físico donde un fluido puede atravesar un material poroso. En términos computacionales, se modela un sistema como una cuadrícula n×n de sitios, donde cada sitio está abierto (puede dejar pasar fluido) o bloqueado (no puede dejar pasar fluido). Un sistema "percola" si existe un camino de sitios abiertos desde la parte superior hasta la parte inferior.

## Requisitos

- Java 8 o superior

## Compilación

Para compilar el proyecto, ejecuta el siguiente comando:

```
./compile.sh
```

Esto compilará todos los archivos Java y colocará los archivos .class en el directorio `out`.

## Ejecución

El proyecto proporciona tres formas diferentes de ejecutar la simulación de percolación:

### 1. Visualizador de Percolación

Este visualizador muestra una simulación basada en un archivo de datos. Para ejecutarlo:

```
./run_percolation.sh data/input10.txt
```

Puedes reemplazar `input10.txt` con cualquier otro archivo de datos en el directorio `data/`.

### 2. Visualizador Interactivo

Este visualizador te permite interactuar con el sistema haciendo clic para abrir sitios. Para ejecutarlo:

```
./run_interactive.sh 10
```

El parámetro `10` especifica el tamaño de la cuadrícula (10×10 en este caso).

### 3. Estadísticas de Percolación

Este programa realiza múltiples experimentos para estimar el umbral de percolación. Para ejecutarlo:

```
./run_stats.sh 20 100
```

El primer parámetro (`20`) especifica el tamaño de la cuadrícula, y el segundo parámetro (`100`) especifica el número de experimentos a realizar.

## Archivos de Datos

El directorio `data/` contiene varios archivos de datos para probar el sistema de percolación. Cada archivo tiene el formato:

1. Primera línea: tamaño n del sistema n×n
2. Líneas siguientes: pares de coordenadas (i, j) que representan sitios a abrir

## Estructura del Proyecto

- `src/`: Contiene los archivos de código fuente Java
  - `Percolation.java`: Implementa el modelo de percolación
  - `PercolationStats.java`: Realiza análisis estadísticos sobre el umbral de percolación
  - `PercolationVisualizer.java`: Proporciona visualización gráfica del sistema de percolación
  - `InteractivePercolationVisualizer.java`: Permite interactuar con el sistema de percolación
- `lib/`: Contiene las bibliotecas necesarias
- `data/`: Contiene archivos de datos de prueba
- `out/`: Contiene archivos compilados 