package cgpi.vtec.controllers;

import cgpi.vtec.exception.VFXMLLoaderException;
import cgpi.vtec.exporter.BaseDesenhoExporter;
import cgpi.vtec.exporter.BaseDesenhoImporter;
import cgpi.vtec.exporter.Exporter;
import cgpi.vtec.exporter.Importer;
import cgpi.vtec.models.DesenhoModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vitor.alves
 */
public abstract class AbstractDesenhoController<T extends DesenhoModel> {

    private AbstractDesenhoController parent;

    private final Map<String, Node> components;

    private T model;

    public AbstractDesenhoController() {
        components = new HashMap<>();
        model = this.createModel();
    }

    protected Exporter getExporter() {
        return new BaseDesenhoExporter(model.getDesenho(), (Canvas) get("canvas"));
    }

    protected Importer getImporter() {
        return new BaseDesenhoImporter(this);
    }

    protected abstract T createModel();

    public Node get(String key) {
        return components.get(key);
    }

    public T getModel() {
        return model;
    }

    public AbstractDesenhoController getParent() {
        return parent;
    }

    @FXML
    private void export() {
        getExporter().export();
    }

    @FXML
    private void importe() {
        try {
            getImporter().importe();
        } catch (VFXMLLoaderException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void limpe() {
        Canvas canvas = (Canvas) this.get("canvas");
        canvas.getGraphicsContext2D().clearRect(canvas.getLayoutX() - 10, canvas.getLayoutY() - 10, canvas.getWidth() + 10, canvas.getHeight() + 10);
        if (parent != null) {
            parent.limpe();
        }
        if (model != null) {
            model.limpe();
        }
    }

}
