package cgpi.controller.poliganocommouse;

import cgpi.vtec.controllers.AbstractDesenhoController;

/**
 * @author vitor.alves
 */
public class PoliganoController extends AbstractDesenhoController<PoliganoModel> {

    @Override
    protected PoliganoModel createModel() {
        return new PoliganoModel();
    }
}
