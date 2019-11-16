package cgpi.vtec.events;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.figuras.actors.Desenhe;
import cgpi.figuras.actors.DesenhoActor;
import cgpi.figuras.model.Desenho;
import cgpi.vtec.exporter.BaseDesenhoImporter;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import static cgpi.figuras.DesenhoUtils.replicarDesenho;

/**
 * @author vitor.alves
 */
public abstract class AbstractDesenhoEvent<T extends Event> implements EventHandler<T> {

    private final AbstractDesenhoController controller;

    private GraphicsContext graphicsContext;

    private Canvas canvas;

    protected final Desenhe desenhoActor;

    protected final Desenho desenho;

    public AbstractDesenhoEvent(AbstractDesenhoController controller) {
        this.controller = controller;
        this.canvas = (Canvas) controller.get("canvas");
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        desenhoActor = new DesenhoActor(this.getGraphicsContext());
        desenho = new Desenho();
    }

    public AbstractDesenhoController getController() {
        return controller;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Desenho getDesenho() {
        return desenho;
    }

    public void handle(T event) {
        executeEvent(event);
        replicateEvent();
    }

    protected void replicateEvent() {
        replicarDesenho(this.controller, this.desenho);
    }

    protected abstract void executeEvent(T event);
}
