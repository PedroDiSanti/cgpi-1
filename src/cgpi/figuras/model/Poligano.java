package cgpi.figuras.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vitor.alves
 */
public class Poligano extends Figura {

    private List<Reta> retas;

    public Poligano(List<Reta> retas) {
        this.retas = new ArrayList<>(retas);
    }

    public List<Reta> getRetas() {
        return retas;
    }
}
