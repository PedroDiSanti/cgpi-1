package cgpi.controller.retacommouse;

import cgpi.figuras.model.Desenho;
import cgpi.vtec.annotation.OnMouseClick;
import cgpi.vtec.models.DesenhoModel;

/**
 * @author vitor.alves
 */
public class RetaComMouseModel implements DesenhoModel {

    private Desenho desenho;

    @Override
    public void limpe() {
        desenho.limpe();
    }

    @Override
    public Desenho getDesenho() {
        return desenho;
    }

    @OnMouseClick(value = "canvas", event = RetaComMouseEvent.class)
    public void setDesenho(Desenho desenho) {
        this.desenho = desenho;
    }
}
