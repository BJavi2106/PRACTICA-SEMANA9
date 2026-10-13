# PRACTICA-SEMANA9 — Hilos en JavaFX

Práctica de Programación de Aplicaciones de Escritorio (UAM), Unidad III: programación concurrente.

## Objetivo
Simular el procesamiento de registros en segundo plano mediante hilos Java, manteniendo receptiva la interfaz JavaFX.

## Funcionalidades
- Procesa 100 registros de clientes y 50 de productos.
- Ejecuta los dos procesos en hilos independientes con tiempos distintos.
- Muestra una barra de progreso y porcentaje para cada proceso.
- Permite iniciar y detener cada proceso de forma individual.
- Evita iniciar dos veces simultáneamente el mismo proceso.
- Permite reiniciar ambos procesos cuando ya no estén activos.
- Actualiza controles JavaFX con `Platform.runLater()`.
- Maneja la detención mediante `interrupt()` e `InterruptedException`.

## Requisitos
- JDK 21.
- IntelliJ IDEA.
- Conexión a Internet para descargar dependencias Maven en la primera ejecución.

## Ejecutar
Abre la carpeta del proyecto en IntelliJ como proyecto Maven y ejecuta en la terminal:

```bash
mvn clean javafx:run
```

También se puede ejecutar el objetivo `javafx:run` desde el panel Maven de IntelliJ.

## Pruebas funcionales realizadas
- La ventana JavaFX abre correctamente.
- Los procesos de clientes y productos llegan al 100 %.
- La interfaz muestra porcentajes y estados de cada proceso.
- Se comprobó el uso de los controles de inicio, detención y reinicio.
- La prueba de concurrencia consiste en iniciar ambos procesos y observar sus barras de progreso por separado.

## Estructura
- `MainApp.java`: punto de entrada JavaFX y carga del FXML.
- `ProcesadorController.java`: creación y control de los hilos, actualización de progreso y detención.
- `procesador-view.fxml`: interfaz gráfica.
- `pom.xml`: dependencias JavaFX y configuración Maven.

## Evidencias
Añade en la carpeta `evidencias/` capturas propias que muestren:
1. La ventana inicial.
2. Los dos procesos avanzando simultáneamente.
3. La detención de un proceso mientras el otro continúa.
4. Ambos procesos finalizados y/o el reinicio.

## Conceptos principales
`Thread`, `Runnable`, `start()`, `Thread.sleep()`, `Platform.runLater()`, `isAlive()`, `interrupt()`, `isInterrupted()` e `InterruptedException`.
