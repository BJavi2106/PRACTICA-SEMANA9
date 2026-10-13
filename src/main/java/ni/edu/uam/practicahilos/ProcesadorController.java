package ni.edu.uam.practicahilos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProcesadorController {
    @FXML private Label lblEstado;
    @FXML private Label lblEstadoClientes;
    @FXML private Label lblEstadoProductos;
    @FXML private Label lblPorcentajeClientes;
    @FXML private Label lblPorcentajeProductos;
    @FXML private ProgressBar barraClientes;
    @FXML private ProgressBar barraProductos;
    @FXML private Button btnIniciarClientes;
    @FXML private Button btnDetenerClientes;
    @FXML private Button btnIniciarProductos;
    @FXML private Button btnDetenerProductos;
    @FXML private Button btnReiniciar;

    private Thread hiloClientes;
    private Thread hiloProductos;

    @FXML
    private void initialize() {
        actualizarBotones();
    }

    @FXML
    private void iniciarClientes() {
        if (estaActivo(hiloClientes)) return;

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Procesando clientes...");
        lblEstado.setText("Proceso de clientes en ejecución.");

        hiloClientes = crearHilo("Hilo-Clientes", 100, 100,
                avance -> {
                    barraClientes.setProgress(avance / 100.0);
                    lblPorcentajeClientes.setText(avance + "%");
                },
                estado -> {
                    lblEstadoClientes.setText(estado);
                    lblEstado.setText("Clientes: " + estado);
                    actualizarBotones();
                });
        hiloClientes.start();
        actualizarBotones();
    }

    @FXML
    private void iniciarProductos() {
        if (estaActivo(hiloProductos)) return;

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Procesando productos...");
        lblEstado.setText("Proceso de productos en ejecución.");

        hiloProductos = crearHilo("Hilo-Productos", 50, 180,
                avance -> {
                    barraProductos.setProgress(avance / 50.0);
                    lblPorcentajeProductos.setText((avance * 100 / 50) + "%");
                },
                estado -> {
                    lblEstadoProductos.setText(estado);
                    lblEstado.setText("Productos: " + estado);
                    actualizarBotones();
                });
        hiloProductos.start();
        actualizarBotones();
    }

    private Thread crearHilo(String nombre, int total, long pausa,
                              ActualizacionProgreso progreso,
                              ActualizacionEstado estadoFinal) {
        Runnable tarea = () -> {
            try {
                for (int i = 1; i <= total; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                    Thread.sleep(pausa);
                    int avance = i;
                    Platform.runLater(() -> progreso.actualizar(avance));
                }
                Platform.runLater(() -> estadoFinal.actualizar("Proceso finalizado."));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> estadoFinal.actualizar("Proceso detenido."));
            }
        };
        Thread hilo = new Thread(tarea, nombre);
        hilo.setDaemon(true);
        return hilo;
    }

    @FXML
    private void detenerClientes() {
        if (estaActivo(hiloClientes)) {
            lblEstadoClientes.setText("Solicitando detención...");
            lblEstado.setText("Deteniendo proceso de clientes...");
            hiloClientes.interrupt();
        }
    }

    @FXML
    private void detenerProductos() {
        if (estaActivo(hiloProductos)) {
            lblEstadoProductos.setText("Solicitando detención...");
            lblEstado.setText("Deteniendo proceso de productos...");
            hiloProductos.interrupt();
        }
    }

    @FXML
    private void reiniciarProceso() {
        if (estaActivo(hiloClientes) || estaActivo(hiloProductos)) {
            lblEstado.setText("Detenga los procesos antes de reiniciar.");
            return;
        }
        barraClientes.setProgress(0);
        barraProductos.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblPorcentajeProductos.setText("0%");
        lblEstadoClientes.setText("Esperando inicio");
        lblEstadoProductos.setText("Esperando inicio");
        lblEstado.setText("Listo para iniciar los procesos.");
        hiloClientes = null;
        hiloProductos = null;
        actualizarBotones();
    }

    private boolean estaActivo(Thread hilo) {
        return hilo != null && hilo.isAlive();
    }

    private void actualizarBotones() {
        boolean clientesActivos = estaActivo(hiloClientes);
        boolean productosActivos = estaActivo(hiloProductos);
        btnIniciarClientes.setDisable(clientesActivos);
        btnDetenerClientes.setDisable(!clientesActivos);
        btnIniciarProductos.setDisable(productosActivos);
        btnDetenerProductos.setDisable(!productosActivos);
        btnReiniciar.setDisable(clientesActivos || productosActivos);
    }

    public void detenerAlCerrar() {
        if (estaActivo(hiloClientes)) hiloClientes.interrupt();
        if (estaActivo(hiloProductos)) hiloProductos.interrupt();
    }

    @FunctionalInterface
    private interface ActualizacionProgreso {
        void actualizar(int avance);
    }

    @FunctionalInterface
    private interface ActualizacionEstado {
        void actualizar(String estado);
    }
}
