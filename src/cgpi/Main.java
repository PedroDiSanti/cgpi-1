package cgpi;

import cgpi.controller.MainController;
import cgpi.vtec.SceneManager;
import cgpi.vtec.VFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static cgpi.view.enums.Scenes.MAIN;

/**
 * @author vitor.alves
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        VFXMLLoader loader = new VFXMLLoader(MAIN, primaryStage);
        Parent root = loader.load();
        primaryStage.setTitle(MAIN.getTitel());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
