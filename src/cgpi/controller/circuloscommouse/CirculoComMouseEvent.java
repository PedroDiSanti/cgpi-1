package cgpi.controller.circuloscommouse;

import cgpi.figuras.model.Circulo;
import cgpi.figuras.model.Ponto;
import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.events.AbstractDesenhoEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * @author vitor.alves
 */
public class CirculoComMouseEvent extends AbstractDesenhoEvent<MouseEvent> {

    private boolean primeiraVez = true;

    private Ponto pontoA;

    private Ponto pontoB;

    public CirculoComMouseEvent(AbstractDesenhoController controller) {
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
        if (primeiraVez == true) {
            pontoA = new Ponto((int) mouseEvent.getX(), (int) mouseEvent.getY());
            this.desenho.addPonto(pontoA);
            primeiraVez = false;
        } else {
            int x2 = (int) mouseEvent.getX();
            int y2 = (int) mouseEvent.getY();
            double raio = pontoA.distance(x2, y2);
            pontoB = new Ponto(x2, y2);
            this.desenho.addCirculo(new Circulo(pontoA, pontoB, raio));
            primeiraVez = true;
        }
    }
}
