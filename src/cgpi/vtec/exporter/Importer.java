package cgpi.vtec.exporter;

import cgpi.vtec.exception.VFXMLLoaderException;

/**
 * @author vitor.alves
 */
public interface Importer {

    void importe() throws VFXMLLoaderException;
}
