package cgpi.controller;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.controllers.Inicializavel;
import cgpi.vtec.SceneManager;
import cgpi.vtec.control.SceneButton;
import cgpi.vtec.exporter.Exporter;
import cgpi.vtec.models.DesenhoModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import static cgpi.view.enums.Scenes.RETA_COM_MOUSE;

/**
 * @author vitor.alves
 */
public class MainController extends AbstractDesenhoController implements Inicializavel {

    private SceneManager sceneManager;

    public MainController() {
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    protected Exporter getExporter() {
        return null;
    }

    @Override
    protected DesenhoModel createModel() {
        return null;
    }

    @Override
    public void inicialize() {
        ((BorderPane) this.get("borderPane")).setCenter(this.sceneManager.getScene(RETA_COM_MOUSE, this));
    }

    @FXML
    private void setScene(Event event) {
        SceneButton source = (SceneButton) event.getSource();
        ((BorderPane) this.get("borderPane")).setCenter(this.sceneManager.getScene(source.getScenes(), this));
        limpe();
    }
}
