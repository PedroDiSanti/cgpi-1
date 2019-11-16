package cgpi.controller.kochsnowflake;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.figuras.model.Ponto;
import cgpi.figuras.model.Reta;
import cgpi.vtec.events.AbstractDesenhoEvent;
import javafx.scene.input.MouseEvent;

import static java.lang.Integer.parseInt;

/**
 * @author vitor.alves
 */
public class KochSnowFlakeEvent extends AbstractDesenhoEvent<MouseEvent> {

    public KochSnowFlakeEvent(AbstractDesenhoController controller) {
        super(controller);
    }

    @Override
    protected void executeEvent(MouseEvent event) {
        KochSnowFlakeModel model = (KochSnowFlakeModel) this.getController().getModel();
        int level = parseInt(model.getLevel());
        drawSnow(level, 20, 280, 280, 280);
        drawSnow(level, 280, 280, 150, 20);
        drawSnow(level, 150, 20, 20, 280);

        desenhoActor.desenhe(this.desenho);
    }


    private void drawSnow(int lev, int x1, int y1, int x5, int y5) {
        int deltaX, deltaY, x2, y2, x3, y3, x4, y4;

        if (lev == 0) {
            this.desenho.addReta(new Reta(new Ponto(x1, y1), new Ponto(x5, y5)));
        }
        if (lev != 0) {
            deltaX = x5 - x1;
            deltaY = y5 - y1;

            x2 = x1 + deltaX / 3;
            y2 = y1 + deltaY / 3;

            x3 = (int) (0.5 * (x1 + x5) + Math.sqrt(3) * (y1 - y5) / 6);
            y3 = (int) (0.5 * (y1 + y5) + Math.sqrt(3) * (x5 - x1) / 6);

            x4 = x1 + 2 * deltaX / 3;
            y4 = y1 + 2 * deltaY / 3;

            drawSnow(lev - 1, x1, y1, x2, y2);
            drawSnow(lev - 1, x2, y2, x3, y3);
            drawSnow(lev - 1, x3, y3, x4, y4);
            drawSnow(lev - 1, x4, y4, x5, y5);
        }
    }
}
