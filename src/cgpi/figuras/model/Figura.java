package cgpi.figuras.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vitor.alves
 */
public class Figura {

    private final Map<String, Ponto> points;
    private final List<String> colors;

    public Figura() {
        points = new HashMap<String, Ponto>();
        colors = new ArrayList<String>();
    }

    public void addPoint(String pointName, Ponto point) {
        points.put(pointName, point);
    }

    public Ponto getPoint(String pointName) {
        return points.get(pointName);
    }

    public List<Ponto> getPontos() {
        return new ArrayList<Ponto>(points.values());
    }

    public void addColor(String color) {
        colors.add(color);
    }
}
