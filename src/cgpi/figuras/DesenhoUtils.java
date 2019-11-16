package cgpi.figuras;

import cgpi.figuras.actors.DesenhoActor;
import cgpi.figuras.model.*;
import cgpi.vtec.controllers.AbstractDesenhoController;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vitor.alves
 */
public class DesenhoUtils {

    public static Desenho convertDesenho(Canvas from, Canvas to, Desenho desenho) {
        Desenho newDesenho = new Desenho();

        newDesenho.addPontos(convertPontos(from, to, desenho.getPontos()));
        newDesenho.addRetas(convertRetas(from, to, desenho.getRetas()));
        newDesenho.addCirculos(convertCirculos(from, to, desenho.getCirculos()));
        newDesenho.addRetangulos(convertRetangulos(from, to, desenho.getRetangulos()));
        newDesenho.addPoliganos(convertPoliganos(from, to, desenho.getPoliganos()));

        return newDesenho;
    }

    private static List<Poligano> convertPoliganos(Canvas from, Canvas to, List<Poligano> poliganos) {
        List<Poligano> newPoliganos = new ArrayList<>();
        for (Poligano poligano : poliganos) {
            newPoliganos.add(new Poligano(convertRetas(from, to, poligano.getRetas())));
        }
        return newPoliganos;
    }

    private static List<Retangulo> convertRetangulos(Canvas from, Canvas to, List<Retangulo> retangulos) {
        List<Retangulo> newRetangulos = new ArrayList<>();
        for (Retangulo retangulo : retangulos) {
            newRetangulos.add(new Retangulo(convertRetas(from, to, retangulo.getRetas())));
        }
        return newRetangulos;
    }

    private static List<Circulo> convertCirculos(Canvas from, Canvas to, List<Circulo> circulos) {
        List<Circulo> newCirculos = new ArrayList<>();
        for (Circulo circulo : circulos) {
            newCirculos.add(new Circulo(convertPontos(from, to, circulo.getPontos())));
        }
        return newCirculos;
    }

    private static List<Reta> convertRetas(Canvas from, Canvas to, List<Reta> retas) {
        List<Reta> newRetas = new ArrayList<>();

        for (Reta reta : retas) {
            newRetas.add(new Reta(convertPontos(from, to, reta.getPontos())));
        }

        return newRetas;
    }

    private static List<Ponto> convertPontos(Canvas from, Canvas to, List<Ponto> pontos) {
        double p1x, p1y;

        List<Ponto> newPontos = new ArrayList<>();
        for (Ponto ponto : pontos) {

            p1x = (to.getWidth() / from.getWidth()) * ponto.getX();
            p1y = (to.getHeight() / from.getHeight()) * ponto.getY();

            newPontos.add(new Ponto(p1x, p1y));
        }

        return newPontos;
    }

    public static void replicarDesenho(AbstractDesenhoController controller, Desenho desenho) {
        AbstractDesenhoController parent = controller.getParent();
        if (parent != null) {
            Canvas canvas = (Canvas) parent.get("canvas");
            Desenho desenhoConvertido = DesenhoUtils.convertDesenho((Canvas) controller.get("canvas"), canvas, desenho);

            DesenhoActor desenhoActorParent = new DesenhoActor(canvas.getGraphicsContext2D());
            desenhoActorParent.desenhe(desenhoConvertido);
        }
    }
}
