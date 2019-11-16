package cgpi.controller.retangulocommouse;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.exporter.BaseDesenhoExporter;
import cgpi.vtec.exporter.Exporter;

/**
 * @author vitor.alves
 */
public class RetanguloController extends AbstractDesenhoController<RetanguloModel> {

    @Override
    protected RetanguloModel createModel() {
        return new RetanguloModel();
    }
}
