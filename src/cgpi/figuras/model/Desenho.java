package cgpi.figuras.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vitor.alves
 */
public class Desenho {

    private final List<Reta> retas;

    private final List<Circulo> circulos;

    private final List<Ponto> pontos;


    public Desenho() {
        this.retas = new ArrayList<>();
        this.circulos = new ArrayList<>();
        pontos = new ArrayList<>();
    }

    public List<Reta> getRetas() {
        return retas;
    }

    public List<Circulo> getCirculos() {
        return circulos;
    }

    public List<Ponto> getPontos() {
        return pontos;
    }

    public void addReta(Reta reta) {
        this.retas.add(reta);
    }

    public void addCirculos(Circulo circulo) {
        this.circulos.add(circulo);
    }

    public void addPonto(Ponto ponto) {
        this.pontos.add(ponto);
    }
}
