package cgpi.figuras.model;

import javafx.geometry.Point2D;

import java.util.List;

/**
 * @author vitor.alves
 */
public class Circulo extends Figura {

    private static final String CENTRO = "centro";

    private static final String BORDA = "borda";

    private final double raio;

    public Circulo(Ponto centro, Ponto borda,double raio) {
        this.addPoint(CENTRO, centro);
        this.addPoint(BORDA, borda);
        this.raio = raio;
    }

    public Circulo(List<Ponto> pontos) {
        this.addPoint(CENTRO, pontos.get(0));
        this.addPoint(BORDA, pontos.get(1));
        this.raio = 10;
    }

    public Ponto getCentro() {
        return this.getPoint(CENTRO);
    }

    public Ponto getBorda() {
        return this.getPoint(BORDA);
    }

    public double getRaio() {
        return raio;
    }
}
