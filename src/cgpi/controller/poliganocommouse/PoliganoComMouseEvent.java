package cgpi.controller.poliganocommouse;

import cgpi.figuras.model.Poligano;
import cgpi.figuras.model.Ponto;
import cgpi.figuras.model.Reta;
import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.events.AbstractDesenhoEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vitor.alves
 */
public class PoliganoComMouseEvent extends AbstractDesenhoEvent<MouseEvent> {

    private boolean primeiraVez = true;

    private List<Reta> retas;

    private Ponto pontoA;

    private Ponto pontoB;

    public PoliganoComMouseEvent(AbstractDesenhoController controller) {
        super(controller);
        this.retas = new ArrayList<>();
    }

    @Override
    protected void executeEvent(MouseEvent mouseEvent) {
        MouseButton button = mouseEvent.getButton();
        if (button.equals(MouseButton.PRIMARY)) {
            onPrimaryClick(mouseEvent);
        } else if (button.equals(MouseButton.SECONDARY)) {
            onSecondaryClick(mouseEvent);
        }
        desenhoActor.desenhe(desenho);
    }

    private void onSecondaryClick(MouseEvent mouseEvent) {
        if (retas.isEmpty()) {
            return;
        }
        retas.add(new Reta(pontoA, retas.get(0).getPointA()));
        primeiraVez = true;
        desenho.addPoligano(new Poligano(retas));
        retas.clear();
    }

    private void onPrimaryClick(MouseEvent mouseEvent) {
        if (primeiraVez) {
            pontoA = new Ponto((int) mouseEvent.getX(), (int) mouseEvent.getY());
            desenho.addPonto(pontoA);
            primeiraVez = false;
        } else {
            pontoB = new Ponto((int) mouseEvent.getX(), (int) mouseEvent.getY());
            desenho.addPonto(pontoB);
            Reta reta = new Reta(pontoA, pontoB);
            retas.add(reta);
            desenho.addRetas(retas);
            pontoA = pontoB;
        }
    }
}
