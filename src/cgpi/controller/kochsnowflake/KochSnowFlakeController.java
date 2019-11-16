package cgpi.controller.kochsnowflake;

import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.exporter.BaseDesenhoExporter;
import cgpi.vtec.exporter.Exporter;

/**
 * @author vitor.alves
 */
public class KochSnowFlakeController extends AbstractDesenhoController<KochSnowFlakeModel> {

    @Override
    protected KochSnowFlakeModel createModel() {
        return new KochSnowFlakeModel();
    }
}
