package cgpi.controller.retangulocommouse;

import cgpi.figuras.model.Ponto;
import cgpi.figuras.model.Reta;
import cgpi.figuras.model.Retangulo;
import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.events.AbstractDesenhoEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * @author vitor.alves
 */
public class RetanguloComMouseEvent extends AbstractDesenhoEvent<MouseEvent> {

    private boolean primeiraVez = true;

    private Ponto pontoA;

    private Ponto pontoB;

    private Ponto pontoC;

    private Ponto pontoD;

    public RetanguloComMouseEvent(AbstractDesenhoController controller) {
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
            pontoC = new Ponto(pontoB.getX(), pontoA.getY());
            pontoD = new Ponto(pontoA.getX(), pontoB.getY());
            desenho.addPonto(pontoB);
            desenho.addPonto(pontoC);
            desenho.addPonto(pontoD);
            Retangulo retangulo = new Retangulo(new Reta(pontoA, pontoC), new Reta(pontoA, pontoD),
                    new Reta(pontoC, pontoB), new Reta(pontoD, pontoB));

            desenho.addRetangulo(retangulo);
            primeiraVez = true;
        }
    }
}
