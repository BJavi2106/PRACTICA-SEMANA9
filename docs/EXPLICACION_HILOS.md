# Explicación breve de los hilos implementados

La aplicación utiliza dos objetos `Thread`, cada uno construido con una tarea `Runnable`. El hilo de clientes simula 100 registros y pausa 100 milisegundos por registro; el hilo de productos simula 50 registros y pausa 180 milisegundos por registro. Como son hilos independientes, ambos procesos pueden avanzar al mismo tiempo sin ejecutar el trabajo prolongado en el hilo de interfaz de JavaFX.

Cada avance se comunica a la interfaz con `Platform.runLater()`, porque los controles JavaFX deben modificarse desde el hilo de aplicación JavaFX. Los botones de detención llaman a `interrupt()`; el hilo detecta la interrupción durante `Thread.sleep()`, captura `InterruptedException` y actualiza el estado a “Proceso detenido”. El método `isAlive()` permite impedir que se inicie de nuevo el mismo proceso mientras todavía está activo. El reinicio se habilita cuando ambos hilos ya terminaron.

## Clases
- **MainApp:** inicia JavaFX, carga la vista FXML y solicita detener los hilos al cerrar la ventana.
- **ProcesadorController:** administra los hilos, actualiza los porcentajes y controla los botones.
- **procesador-view.fxml:** define las dos barras de progreso y los controles de cada proceso.
