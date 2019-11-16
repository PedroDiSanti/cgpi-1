package cgpi.controller.retacommouse;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.exporter.BaseDesenhoExporter;
import cgpi.vtec.exporter.Exporter;

/**
 * @author vitor.alves
 */
public class RetaComMouseController extends AbstractDesenhoController<RetaComMouseModel> {

    @Override
    protected RetaComMouseModel createModel() {
        return new RetaComMouseModel();
    }
}
