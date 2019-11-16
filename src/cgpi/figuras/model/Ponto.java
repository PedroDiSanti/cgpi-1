package cgpi.figuras.model;

import javafx.geometry.Point2D;

/**
 * @author vitor.alves
 */
public class Ponto extends Point2D {

    public final static int DIAMETRO = 6;

    /**
     * Creates a new instance of {@code Point2D}.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Ponto(double x, double y) {
        super(x, y);
    }
}
