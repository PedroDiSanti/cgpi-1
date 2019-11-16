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

    private final List<Cor> cores;

    private final List<Retangulo> retangulos;

    private final List<Poligano> poliganos;


    public Desenho() {
        this.retas = new ArrayList<>();
        this.circulos = new ArrayList<>();
        pontos = new ArrayList<>();
        cores = new ArrayList<>();
        retangulos = new ArrayList<>();
        poliganos = new ArrayList<>();
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

    public List<Retangulo> getRetangulos() {
        return retangulos;
    }

    public void addReta(Reta reta) {
        this.retas.add(reta);
    }

    public void addCirculo(Circulo circulo) {
        this.circulos.add(circulo);
    }

    public void addPonto(Ponto ponto) {
        this.pontos.add(ponto);
    }

    public void addCor(Cor cor) { this.cores.add(cor); }

    public void addRetangulo(Retangulo retangulo) {
        this.retangulos.add(retangulo);
    }

    public void addPoligano(Poligano poligano) {
        this.poliganos.add(poligano);
    }

    public void addPontos(List<Ponto> pontos) {
        this.pontos.addAll(pontos);
    }

    public void addRetas(List<Reta> retas) {
        this.retas.addAll(retas);
    }

    public void addCores(List<Cor> cores) {
        this.cores.addAll(cores);
    }

    public void addCirculos(List<Circulo> circulos) {
        this.circulos.addAll(circulos);
    }

    public void addRetangulos(List<Retangulo> retangulos) {
        this.retangulos.addAll(retangulos);
    }

    public void addPoliganos(List<Poligano> poliganos) {
        this.poliganos.addAll(poliganos);
    }

    public void limpe() {
        retas.clear();
        circulos.clear();
        pontos.clear();
        cores.clear();
        retangulos.clear();
        poliganos.clear();
    }

    public List<Poligano> getPoliganos() {
        return this.poliganos;
    }
}
