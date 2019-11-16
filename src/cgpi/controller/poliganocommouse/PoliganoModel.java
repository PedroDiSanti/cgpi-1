package cgpi.controller.poliganocommouse;

import cgpi.figuras.model.Desenho;
import cgpi.vtec.annotation.OnMouseClick;
import cgpi.vtec.models.DesenhoModel;

/**
 * @author vitor.alves
 */
public class PoliganoModel implements DesenhoModel {

    private Desenho desenho;

    @Override
    public void limpe() {
        desenho.limpe();
    }

    @Override
    public Desenho getDesenho() {
        return desenho;
    }

    @OnMouseClick(value = "canvas", event = PoliganoComMouseEvent.class)
    public void setDesenho(Desenho desenho) {
        this.desenho = desenho;
    }
}
