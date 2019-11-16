package cgpi.controller.retacommouse;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.figuras.model.Ponto;
import cgpi.figuras.model.Reta;
import cgpi.vtec.events.AbstractDesenhoEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * @author vitor.alves
 */
public class RetaComMouseEvent extends AbstractDesenhoEvent<MouseEvent> {

    private boolean primeiraVez = true;

    private Ponto pontoA;

    private Ponto pontoB;

    public RetaComMouseEvent(AbstractDesenhoController controller) {
        super(controller);
    }

    @Override
    protected void executeEvent(MouseEvent mouseEvent) {
        MouseButton button = mouseEvent.getButton();
        if (button.equals(MouseButton.PRIMARY)) {
            onPrimaryClick(mouseEvent);
        }
        desenhoActor.desenhe(this.desenho);
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
            this.desenho.addReta(reta);
            primeiraVez = true;
        }
    }
}
