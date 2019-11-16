package cgpi.figuras.model;

import javafx.scene.paint.Color;

import java.util.List;

/**
 * @author vitor.alves
 */
public class Reta extends Figura {

    private static final String POINTO_A = "A";

    private static final String POINTO_B = "B";

    int R = (int)(Math.random()*256);
    int G = (int)(Math.random()*256);
    int B= (int)(Math.random()*256);

    private final Color corReta = Color.rgb(R, G, B);

    public Reta(Ponto pointA, Ponto pointB) {
        this.addPoint(POINTO_A, pointA);
        this.addPoint(POINTO_B, pointB);
    }

    public Reta(List<Ponto> pontos) {
        this.addPoint(POINTO_A, pontos.get(0));
        this.addPoint(POINTO_B, pontos.get(1));
    }

    public Ponto getPointA() {
        return this.getPoint(POINTO_A);
    }

    public Ponto getPointB() {
        return this.getPoint(POINTO_B);
    }

    public double[] getPointACoordinates() {
        return new double[]{this.getPointA().getX(), this.getPointA().getY()};
    }

    public double[] getPointBCoordinates() {
        return new double[]{this.getPointB().getX(), this.getPointB().getY()};
    }

    public int getColorR() { return  this.R; }

    public int getColorG() { return  this.G; }

    public int getColorB() { return  this.B; }

    public Color setColor() { return corReta; }
}
