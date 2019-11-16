package cgpi.figuras.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * @author vitor.alves
 */
public class Circulo extends Figura {

    private static final String CENTRO = "centro";

    private static final String BORDA = "borda";

    private final double raio;

    int R = (int)(Math.random()*256);

    int G = (int)(Math.random()*256);

    int B= (int)(Math.random()*256);

    private final Color corReta = Color.rgb(R, G, B);

    public Circulo(Ponto centro, Ponto borda,double raio) {
        this.addPoint(CENTRO, centro);
        this.addPoint(BORDA, borda);
        this.raio = raio;
    }

    public Circulo(Ponto centro, double raio) {
        this.addPoint(CENTRO, centro);
        this.addPoint(BORDA, new Ponto(centro.getX() + raio, centro.getY()));
        this.raio = raio;
    }

    public Circulo(List<Ponto> pontos) {
        this.addPoint(CENTRO, pontos.get(0));
        this.addPoint(BORDA, pontos.get(1));
        this.raio = getCentro().distance(getBorda().getX(), getBorda().getY());
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

    public int getColorR() { return  this.R; }

    public int getColorG() { return  this.G; }

    public int getColorB() { return  this.B; }

    public Color setColor() { return corReta; }
}
