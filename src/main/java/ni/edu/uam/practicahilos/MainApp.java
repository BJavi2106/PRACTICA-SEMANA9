package ni.edu.uam.practicahilos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("procesador-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Monitor de procesamiento - JavaFX");
        stage.setScene(scene);
        stage.setMinWidth(620);
        stage.setMinHeight(460);

        ProcesadorController controller = loader.getController();
        stage.setOnCloseRequest(event -> controller.detenerAlCerrar());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
