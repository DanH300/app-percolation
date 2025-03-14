# Notas del Proyecto de Percolación / Percolation Project Notes

## Español

### Introducción
Este documento explica paso a paso cómo se construyó el proyecto de simulación de percolación. El proyecto modela un fenómeno físico donde un fluido atraviesa un material poroso, utilizando estructuras de datos eficientes y algoritmos de conectividad.

### Conceptos Fundamentales
La percolación es un concepto de la física y las matemáticas que estudia el movimiento de fluidos a través de materiales porosos. En nuestro modelo:
- Trabajamos con una cuadrícula de n×n sitios
- Cada sitio puede estar abierto (el fluido puede pasar) o bloqueado
- El sistema "percola" cuando el fluido puede fluir desde la fila superior hasta la fila inferior

### Estructura del Proyecto
El proyecto está organizado en los siguientes componentes:

1. **Clases Principales**:
   - `Percolation.java`: Implementa el modelo de percolación
   - `PercolationStats.java`: Realiza análisis estadístico del umbral de percolación
   - `PercolationVisualizer.java`: Visualiza el proceso de percolación
   - `InteractivePercolationVisualizer.java`: Permite interacción con el modelo

2. **Bibliotecas de Soporte**:
   - `WeightedQuickUnionUF.java`: Implementa el algoritmo Union-Find con ponderación
   - Otras utilidades: `StdDraw`, `StdOut`, `StdRandom`, `StdStats`, `In`

3. **Scripts de Ejecución**:
   - `compile.sh`: Compila el proyecto
   - `run_percolation.sh`: Ejecuta el visualizador con un archivo de datos
   - `run_interactive.sh`: Ejecuta el visualizador interactivo
   - `run_stats.sh`: Ejecuta el análisis estadístico

### Paso a Paso: Construcción del Proyecto

#### 1. Diseño del Modelo de Percolación
El primer paso fue diseñar la clase `Percolation` que modela el sistema:

- **Representación de la cuadrícula**: Utilizamos un array bidimensional de booleanos para representar los sitios (abierto = true, bloqueado = false).
- **Conectividad**: Implementamos dos estructuras Union-Find:
  - Una para determinar si el sistema percola (conectando filas superior e inferior)
  - Otra para determinar si un sitio está lleno (conectado a la fila superior)
- **Sitios virtuales**: Añadimos sitios virtuales para simplificar la detección de percolación.
- **Problema de "backwash"**: Resolvimos el problema de "backwash" utilizando dos estructuras Union-Find separadas.

#### 2. Implementación del Algoritmo Union-Find
El algoritmo Union-Find es fundamental para este proyecto:

- Utilizamos la implementación con ponderación y compresión de caminos para lograr operaciones casi constantes.
- Cada sitio se representa como un nodo en un árbol.
- La operación `union` conecta dos sitios.
- La operación `find` determina si dos sitios están conectados.

#### 3. Análisis Estadístico
Para estimar el umbral de percolación:

- Implementamos simulaciones de Monte Carlo en `PercolationStats`.
- Ejecutamos múltiples experimentos con diferentes tamaños de cuadrícula.
- Calculamos la media, desviación estándar e intervalos de confianza.
- Optimizamos la selección de sitios aleatorios para mejorar el rendimiento.

#### 4. Visualización
Para visualizar el proceso:

- Utilizamos la biblioteca `StdDraw` para crear representaciones gráficas.
- Implementamos un visualizador basado en archivos y otro interactivo.
- Utilizamos colores para distinguir sitios bloqueados, abiertos y llenos.

#### 5. Optimizaciones y Mejoras
A lo largo del desarrollo, implementamos varias optimizaciones:

- **Verificación temprana**: Comprobamos si un sitio ya está abierto antes de procesarlo.
- **Caso especial n=1**: Manejamos el caso especial de una cuadrícula 1×1.
- **Selección eficiente de sitios aleatorios**: Implementamos un algoritmo para evitar intentar abrir sitios ya abiertos.
- **Comentarios personalizados**: Añadimos comentarios explicativos sobre decisiones de diseño y optimizaciones.

#### 6. Control de Versiones
Utilizamos Git para gestionar el desarrollo:

- Creamos ramas separadas para diferentes aspectos del proyecto:
  - `master`: Rama principal de implementación

### Ejemplos de Interacción con el Sistema

A continuación, se presentan ejemplos prácticos de cómo interactuar con el sistema de percolación:

#### Ejemplo 1: Visualizador Interactivo Básico

Este ejemplo muestra cómo utilizar el visualizador interactivo para abrir sitios manualmente y observar el proceso de percolación:

```bash
# Ejecutar el visualizador interactivo con una cuadrícula de 5x5
./run_interactive.sh 5
```

Una vez que se abre la ventana de visualización:
1. Haz clic en diferentes sitios para abrirlos (cambiarán de negro a blanco).
2. Los sitios que se llenan de fluido se mostrarán en azul.
3. Continúa abriendo sitios hasta que el sistema percole (cuando el fluido llegue de arriba a abajo).
4. Observa cómo se forma un camino conectado desde la fila superior hasta la inferior.

#### Ejemplo 2: Análisis Estadístico del Umbral de Percolación

Este ejemplo muestra cómo realizar análisis estadísticos para estimar el umbral de percolación:

```bash
# Ejecutar 50 experimentos en una cuadrícula de 20x20
./run_stats.sh 20 50

# Ejecutar 100 experimentos en una cuadrícula de 50x50
./run_stats.sh 50 100

# Ejecutar 20 experimentos en una cuadrícula de 100x100
./run_stats.sh 100 20
```

Observa cómo el umbral de percolación tiende a converger alrededor de 0.59 a medida que aumenta el tamaño de la cuadrícula y el número de experimentos.

#### Ejemplo 3: Visualización de Patrones Predefinidos

Este ejemplo muestra cómo visualizar patrones de percolación predefinidos:

```bash
# Visualizar un patrón simple de 10x10
./run_percolation.sh data/input10.txt

# Visualizar un patrón más complejo de 20x20
./run_percolation.sh data/input20.txt

# Visualizar un patrón grande de 50x50
./run_percolation.sh data/input50.txt

# Visualizar patrones especiales
./run_percolation.sh data/heart25.txt  # Patrón en forma de corazón
./run_percolation.sh data/snake13.txt  # Patrón en forma de serpiente
```

Estos archivos contienen secuencias predefinidas de sitios para abrir, lo que permite observar comportamientos específicos del sistema.

#### Ejemplo 4: Demostración del Algoritmo Union-Find

Para entender mejor cómo funciona el algoritmo Union-Find en el contexto de percolación:

1. Dibuja una cuadrícula pequeña (por ejemplo, 3x3) en papel.
2. Numera cada celda de 0 a 8 (de izquierda a derecha, de arriba a abajo).
3. Añade dos sitios virtuales: 9 (arriba) y 10 (abajo).
4. Conecta el sitio 9 con las celdas de la fila superior (0, 1, 2).
5. Conecta el sitio 10 con las celdas de la fila inferior (6, 7, 8).
6. Simula la apertura de sitios y observa cómo se conectan:
   - Al abrir el sitio 4 (centro), no hay conexiones.
   - Al abrir el sitio 1 (arriba-centro), se conecta con el sitio virtual 9.
   - Al abrir el sitio 7 (abajo-centro), se conecta con el sitio virtual 10.
   - Al abrir el sitio 4 (centro), se conecta con el sitio 1, formando un árbol.
   - Al abrir el sitio 7 (abajo-centro), se conecta con el sitio 4, conectando los sitios virtuales 9 y 10, lo que indica percolación.

#### Ejemplo 5: Comparación de Diferentes Densidades

Para observar cómo la densidad de sitios abiertos afecta la percolación:

```bash
# Crear tres archivos de datos con diferentes densidades
# (Estos comandos son conceptuales, no ejecutables directamente)

# Baja densidad (20% de sitios abiertos)
./run_interactive.sh 10
# Abre manualmente aproximadamente el 20% de los sitios (unos 20 sitios)

# Media densidad (50% de sitios abiertos)
./run_interactive.sh 10
# Abre manualmente aproximadamente el 50% de los sitios (unos 50 sitios)

# Alta densidad (80% de sitios abiertos)
./run_interactive.sh 10
# Abre manualmente aproximadamente el 80% de los sitios (unos 80 sitios)
```

Observa cómo:
- Con baja densidad, es poco probable que el sistema percole.
- Con densidad media (cerca del umbral de percolación), el sistema puede o no percolar.
- Con alta densidad, es casi seguro que el sistema percole.

### Conclusiones y Aprendizajes
Este proyecto nos permitió:

- Implementar y comprender el algoritmo Union-Find en un contexto práctico.
- Modelar un fenómeno físico utilizando estructuras de datos eficientes.
- Aplicar técnicas de simulación de Monte Carlo para estimar propiedades estadísticas.
- Visualizar procesos complejos de manera intuitiva.
- Practicar buenas técnicas de programación y control de versiones.

## English

### Introduction
This document explains step by step how the percolation simulation project was built. The project models a physical phenomenon where a fluid flows through a porous material, using efficient data structures and connectivity algorithms.

### Fundamental Concepts
Percolation is a concept from physics and mathematics that studies the movement of fluids through porous materials. In our model:
- We work with an n×n grid of sites
- Each site can be open (fluid can flow) or blocked
- The system "percolates" when fluid can flow from the top row to the bottom row

### Project Structure
The project is organized into the following components:

1. **Main Classes**:
   - `Percolation.java`: Implements the percolation model
   - `PercolationStats.java`: Performs statistical analysis of the percolation threshold
   - `PercolationVisualizer.java`: Visualizes the percolation process
   - `InteractivePercolationVisualizer.java`: Allows interaction with the model

2. **Support Libraries**:
   - `WeightedQuickUnionUF.java`: Implements the weighted Union-Find algorithm
   - Other utilities: `StdDraw`, `StdOut`, `StdRandom`, `StdStats`, `In`

3. **Execution Scripts**:
   - `compile.sh`: Compiles the project
   - `run_percolation.sh`: Runs the visualizer with a data file
   - `run_interactive.sh`: Runs the interactive visualizer
   - `run_stats.sh`: Runs the statistical analysis

### Step by Step: Building the Project

#### 1. Percolation Model Design
The first step was to design the `Percolation` class that models the system:

- **Grid Representation**: We used a two-dimensional boolean array to represent sites (open = true, blocked = false).
- **Connectivity**: We implemented two Union-Find structures:
  - One to determine if the system percolates (connecting top and bottom rows)
  - Another to determine if a site is full (connected to the top row)
- **Virtual Sites**: We added virtual sites to simplify percolation detection.
- **Backwash Problem**: We solved the backwash problem by using two separate Union-Find structures.

#### 2. Union-Find Algorithm Implementation
The Union-Find algorithm is fundamental to this project:

- We used the implementation with weighting and path compression to achieve nearly constant operations.
- Each site is represented as a node in a tree.
- The `union` operation connects two sites.
- The `find` operation determines if two sites are connected.

#### 3. Statistical Analysis
To estimate the percolation threshold:

- We implemented Monte Carlo simulations in `PercolationStats`.
- We ran multiple experiments with different grid sizes.
- We calculated the mean, standard deviation, and confidence intervals.
- We optimized random site selection to improve performance.

#### 4. Visualization
To visualize the process:

- We used the `StdDraw` library to create graphical representations.
- We implemented a file-based visualizer and an interactive one.
- We used colors to distinguish blocked, open, and full sites.

#### 5. Optimizations and Improvements
Throughout development, we implemented several optimizations:

- **Early Verification**: We check if a site is already open before processing it.
- **Special Case n=1**: We handle the special case of a 1×1 grid.
- **Efficient Random Site Selection**: We implemented an algorithm to avoid trying to open already open sites.
- **Custom Comments**: We added explanatory comments about design decisions and optimizations.

#### 6. Version Control
We used Git to manage development:

- We created separate branches for different aspects of the project:
  - `master`: Main implementation branch

### Examples of System Interaction

Below are practical examples of how to interact with the percolation system:

#### Example 1: Basic Interactive Visualizer

This example shows how to use the interactive visualizer to manually open sites and observe the percolation process:

```bash
# Run the interactive visualizer with a 5x5 grid
./run_interactive.sh 5
```

Once the visualization window opens:
1. Click on different sites to open them (they will change from black to white).
2. Sites that fill with fluid will be shown in blue.
3. Continue opening sites until the system percolates (when fluid reaches from top to bottom).
4. Observe how a connected path forms from the top row to the bottom row.

#### Example 2: Statistical Analysis of Percolation Threshold

This example shows how to perform statistical analyses to estimate the percolation threshold:

```bash
# Run 50 experiments on a 20x20 grid
./run_stats.sh 20 50

# Run 100 experiments on a 50x50 grid
./run_stats.sh 50 100

# Run 20 experiments on a 100x100 grid
./run_stats.sh 100 20
```

Observe how the percolation threshold tends to converge around 0.59 as the grid size and number of experiments increase.

#### Example 3: Visualization of Predefined Patterns

This example shows how to visualize predefined percolation patterns:

```bash
# Visualize a simple 10x10 pattern
./run_percolation.sh data/input10.txt

# Visualize a more complex 20x20 pattern
./run_percolation.sh data/input20.txt

# Visualize a large 50x50 pattern
./run_percolation.sh data/input50.txt

# Visualize special patterns
./run_percolation.sh data/heart25.txt  # Heart-shaped pattern
./run_percolation.sh data/snake13.txt  # Snake-shaped pattern
```

These files contain predefined sequences of sites to open, allowing observation of specific system behaviors.

#### Example 4: Union-Find Algorithm Demonstration

To better understand how the Union-Find algorithm works in the context of percolation:

1. Draw a small grid (e.g., 3x3) on paper.
2. Number each cell from 0 to 8 (left to right, top to bottom).
3. Add two virtual sites: 9 (top) and 10 (bottom).
4. Connect site 9 with the cells in the top row (0, 1, 2).
5. Connect site 10 with the cells in the bottom row (6, 7, 8).
6. Simulate opening sites and observe how they connect:
   - When opening site 4 (center), there are no connections.
   - When opening site 1 (top-center), it connects with virtual site 9.
   - When opening site 7 (bottom-center), it connects with virtual site 10.
   - When opening site 4 (center), it connects with site 1, forming a tree.
   - When opening site 7 (bottom-center), it connects with site 4, connecting virtual sites 9 and 10, indicating percolation.

#### Example 5: Comparison of Different Densities

To observe how the density of open sites affects percolation:

```bash
# Create three data files with different densities
# (These commands are conceptual, not directly executable)

# Low density (20% of sites open)
./run_interactive.sh 10
# Manually open approximately 20% of the sites (about 20 sites)

# Medium density (50% of sites open)
./run_interactive.sh 10
# Manually open approximately 50% of the sites (about 50 sites)

# High density (80% of sites open)
./run_interactive.sh 10
# Manually open approximately 80% of the sites (about 80 sites)
```

Observe how:
- With low density, the system is unlikely to percolate.
- With medium density (near the percolation threshold), the system may or may not percolate.
- With high density, the system is almost certain to percolate.

### Conclusions and Learnings
This project allowed us to:

- Implement and understand the Union-Find algorithm in a practical context.
- Model a physical phenomenon using efficient data structures.
- Apply Monte Carlo simulation techniques to estimate statistical properties.
- Visualize complex processes intuitively.
- Practice good programming and version control techniques. 