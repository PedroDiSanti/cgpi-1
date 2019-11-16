package cgpi.vtec.exporter;

import cgpi.figuras.model.*;
import javafx.scene.canvas.Canvas;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * @author vitor.alves
 */
public class BaseDesenhoExporter implements Exporter {

    private Desenho desenho;

    private Canvas canvas;

    public BaseDesenhoExporter(Desenho desenho, Canvas canvas) {
        this.desenho = desenho;
        this.canvas = canvas;
    }

    public void setDesenho(Desenho desenho) {
        this.desenho = desenho;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void export() {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("FIGURAS");
            doc.appendChild(rootElement);

            createRetas(doc, rootElement);

            createCirculos(doc, rootElement);

            createRetangulos(doc, rootElement);

            createPoliganos(doc, rootElement);

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("figuras.xml"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createPoliganos(Document doc, Element rootElement) {
        for (Poligano retangulo : desenho.getPoliganos()) {
            Element retanguloTag = doc.createElement("Poliganos");

            Element retanguloPontoA = doc.createElement("Ponto");
//            createPonto(doc, retanguloTag, retanguloPontoA, "x", (getPorporcao(retangulo.getPointA().getX(), canvas.getHeight())));
//            createPonto(doc, retanguloTag, retanguloPontoA, "y", getPorporcao(retangulo.getPointA().getY(), canvas.getWidth()));


            Element retanguloPontoB = doc.createElement("Ponto");
//            createPonto(doc, retanguloTag, retanguloPontoB, "x", getPorporcao(retangulo.getPointB().getX(), canvas.getWidth()));
//            createPonto(doc, retanguloTag, retanguloPontoB, "y", getPorporcao(retangulo.getPointB().getY(), canvas.getHeight()));

            rootElement.appendChild(retanguloTag);
        }
    }

    private void createRetangulos(Document doc, Element rootElement) {
        for (Retangulo retangulo : desenho.getRetangulos()) {
            Element retanguloTag = doc.createElement("Retangulos");

            Element retanguloPontoA = doc.createElement("Ponto");
            createPonto(doc, retanguloTag, retanguloPontoA, "x", (getPorporcao(retangulo.getPointA().getX(), canvas.getHeight())));
            createPonto(doc, retanguloTag, retanguloPontoA, "y", getPorporcao(retangulo.getPointA().getY(), canvas.getWidth()));


            Element retanguloPontoB = doc.createElement("Ponto");
            createPonto(doc, retanguloTag, retanguloPontoB, "x", getPorporcao(retangulo.getPointB().getX(), canvas.getWidth()));
            createPonto(doc, retanguloTag, retanguloPontoB, "y", getPorporcao(retangulo.getPointB().getY(), canvas.getHeight()));

            rootElement.appendChild(retanguloTag);
        }
    }

    private void createCirculos(Document doc, Element rootElement) {
        for (Circulo circulo : desenho.getCirculos()) {
            Element circuloTag = doc.createElement("Circulo");

            Element circuloCentro = doc.createElement("Ponto");
            createPonto(doc, circuloTag, circuloCentro, "x", getPorporcao(circulo.getCentro().getX(), canvas.getWidth()));
            createPonto(doc, circuloTag, circuloCentro, "y", getPorporcao(circulo.getCentro().getY(), canvas.getHeight()));

            createRaio(doc, circuloTag, circulo.getRaio());

            Element cor = doc.createElement("Cor");
            createColor(doc, circuloTag, cor, "R", circulo.getColorR());
            createColor(doc, circuloTag, cor, "G", circulo.getColorG());
            createColor(doc, circuloTag, cor, "B", circulo.getColorB());

            rootElement.appendChild(circuloTag);
        }
    }

    private void createRetas(Document doc, Element rootElement) {
        for (Reta reta : desenho.getRetas()) {
            Element retaTag = doc.createElement("Reta");

            Element retaPontoA = doc.createElement("Ponto");
            createPonto(doc, retaTag, retaPontoA, "x", getPorporcao(reta.getPointA().getX(), canvas.getWidth()));
            createPonto(doc, retaTag, retaPontoA, "y", getPorporcao(reta.getPointA().getY(), canvas.getHeight()));


            Element retaPontoB = doc.createElement("Ponto");
            createPonto(doc, retaTag, retaPontoB, "x", getPorporcao(reta.getPointB().getX(), canvas.getWidth()));
            createPonto(doc, retaTag, retaPontoB, "y", getPorporcao(reta.getPointB().getY(), canvas.getHeight()));

            Element cor = doc.createElement("Cor");
            createColor(doc, retaTag, cor, "R", reta.getColorR());
            createColor(doc, retaTag, cor, "G", reta.getColorG());
            createColor(doc, retaTag, cor, "B", reta.getColorB());

            rootElement.appendChild(retaTag);
        }
    }

    private double getPorporcao(double valeu, double width) {
        return valeu / width;
    }

    private void createPonto(Document doc, Element retaTag, Element retaPonto, String point, double value) {
        Element ponto = doc.createElement(point);
        ponto.setTextContent(String.valueOf(value));
        retaPonto.appendChild(ponto);
        retaTag.appendChild(retaPonto);
    }

    private void createColor(Document doc, Element retaTag, Element retaCor, String colorType, int value) {
        Element cor = doc.createElement(colorType);
        cor.setTextContent(String.valueOf(value));
        retaCor.appendChild(cor);
        retaTag.appendChild(retaCor);
    }

    private void createRaio(Document doc, Element retaTag, double value) {
        Element raio = doc.createElement("Raio");
        raio.setTextContent(String.valueOf(value));
        retaTag.appendChild(raio);
    }
}
