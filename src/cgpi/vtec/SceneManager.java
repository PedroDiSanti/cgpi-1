package cgpi.vtec;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.view.enums.Scenes;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author vitor.alves
 */
public class SceneManager {

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public Node getScene(Scenes scene) {
        VFXMLLoader loader = new VFXMLLoader(scene, stage);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loader.getRoot();
    }

    public Node getScene(Scenes scene, AbstractDesenhoController parent) {
        VFXMLLoader loader = new VFXMLLoader(scene, stage);
        loader.setParentController(parent);
        loader.load();
        stage.setTitle(scene.getTitel());
        return loader.getRoot();
    }
}
