package cgpi.vtec.exporter;

import cgpi.figuras.actors.DesenhoActor;
import cgpi.figuras.model.*;
import cgpi.vtec.controllers.AbstractDesenhoController;
import cgpi.vtec.exception.VFXMLLoaderException;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cgpi.figuras.DesenhoUtils.replicarDesenho;

/**
 * @author vitor.alves
 */
public class BaseDesenhoImporter implements Importer {

    private final Desenho desenho = new Desenho();

    private AbstractDesenhoController controller;

    private Canvas canvas;

    private DesenhoActor actor;

    public BaseDesenhoImporter(AbstractDesenhoController controller) {
        this.controller = controller;
        this.canvas = (Canvas) controller.get("canvas");
        actor = new DesenhoActor(canvas.getGraphicsContext2D());
    }

    @Override
    public void importe() throws VFXMLLoaderException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select desenho");

        File xmlFile = fileChooser.showOpenDialog(new Stage());
        if (xmlFile.getName().contains(".xml")) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            Document doc = null;
            try {
                builder = factory.newDocumentBuilder();
                doc = builder.parse(xmlFile);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (xmlFile.getName().contains(".xml")) {
                getRetas(doc);
                getCirculos(doc);
                getRetangulos(doc);
                getPoligono(doc);
            }
            actor.desenhe(desenho);
            replicateEvent();
        } else {
            throw new VFXMLLoaderException("Arquivo selecionado nao e um xml");
        }
    }

    private void getRetangulos(Document doc) {
        NodeList retangulosNodeList = doc.getElementsByTagName("Retangulo");
        List<Ponto> pontos;
        List<String> cores;
        for (int i = 0; i < retangulosNodeList.getLength(); i++) {
            Node retanguloNode = retangulosNodeList.item(i);
            if (retanguloNode.getNodeType() == Node.ELEMENT_NODE) {
                Element retanguloElemento = (Element) retanguloNode;
                NodeList pontosNode = retanguloElemento.getElementsByTagName("Ponto");

                pontos = getPontos(pontosNode);

                desenho.addRetangulo(new Retangulo(pontos.get(0), pontos.get(1)));
                pontos.clear();

                NodeList coresNode = retanguloElemento.getElementsByTagName("Cor");
                cores = getCores(coresNode);
                desenho.addCor(new Cor(cores));
                cores.clear();
            }
        }
    }

    private void getCirculos(Document doc) {
        NodeList circuloNodeList = doc.getElementsByTagName("Circulo");
        List<Ponto> pontos;
        List<String> cores;
        for (int i = 0; i < circuloNodeList.getLength(); i++) {
            Node circuloNode = circuloNodeList.item(i);
            if (circuloNode.getNodeType() == Node.ELEMENT_NODE) {
                Element circuloElement = (Element) circuloNode;
                NodeList pontosNode = circuloElement.getElementsByTagName("Ponto");

                pontos = getPontos(pontosNode);

                String raio = circuloElement.getElementsByTagName("Raio").item(0).getTextContent();

                double raioTotal = Double.parseDouble(raio) * (canvas.getWidth());
                desenho.addCirculo(new Circulo(pontos.get(0), raioTotal));
                pontos.clear();

                NodeList coresNode = circuloElement.getElementsByTagName("Cor");
                cores = getCores(coresNode);
                desenho.addCor(new Cor(cores));
                cores.clear();
            }
        }
    }

    private void getRetas(Document doc) {
        NodeList retaNodeList = doc.getElementsByTagName("Reta");
        List<Ponto> pontos;
        List<String> cores;
        for (int i = 0; i < retaNodeList.getLength(); i++) {
            Node retaNode = retaNodeList.item(i);
            if (retaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element retaElemento = (Element) retaNode;
                NodeList pontosNode = retaElemento.getElementsByTagName("Ponto");
                pontos = getPontos(pontosNode);
                desenho.addReta(new Reta(pontos));
                pontos.clear();

                NodeList coresNode = retaElemento.getElementsByTagName("Cor");
                cores = getCores(coresNode);
                desenho.addCor(new Cor(cores));
                cores.clear();
            }
        }
    }

    private void getPoligono(Document doc) {
        List<Ponto> pontos;
        List<String> cores;
        List<Reta> retas = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName("Poligono");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node figureNode = nodeList.item(i);

            if (figureNode.getNodeType() == Node.ELEMENT_NODE) {
                Element figureElement = (Element) figureNode;

                NodeList pontosNode = figureElement.getElementsByTagName("Ponto");
                pontos = getPontos(pontosNode);

                for (int k = 0; k < pontos.size(); k++) {
                    if (k%2 != 0) {
                        retas.add(new Reta(pontos.get(k), pontos.get(k - 1)));
                    }
                }
                desenho.addPoligano(new Poligano(retas));
                retas.clear();
                pontos.clear();

                NodeList coresNode = figureElement.getElementsByTagName("Cor");
                cores = getCores(coresNode);
                desenho.addCor(new Cor(cores));
                cores.clear();
            }
        }
    }

    private List<Ponto> getPontos(NodeList pontosNode) {
        List<Ponto> pontos = new ArrayList<>();
        for (int k = 0; k < pontosNode.getLength(); k++) {
            Element pontoElement = (Element) pontosNode.item(k);
            Node xFile = pontoElement.getElementsByTagName("x").item(0);
            double x = Double.parseDouble(xFile.getTextContent()) * canvas.getWidth();
            Node yFile = pontoElement.getElementsByTagName("y").item(0);
            double y = Double.parseDouble(yFile.getTextContent()) * canvas.getHeight();
            pontos.add(new Ponto(x, y));
        }
        return pontos;
    }

    private List<String> getCores(NodeList coresNode) {
        List<String> cores = new ArrayList<>();
        for (int k = 0; k < coresNode.getLength(); k++) {
            Element corElement = (Element) coresNode.item(k);

            Node rFile = corElement.getElementsByTagName("R").item(0);
            String red = rFile.getTextContent();

            Node gFile = corElement.getElementsByTagName("G").item(0);
            String green = gFile.getTextContent();

            Node bFile = corElement.getElementsByTagName("B").item(0);
            String blue = bFile.getTextContent();

            cores.add(red);
            cores.add(green);
            cores.add(blue);
        }
        return cores;
    }

    protected void replicateEvent() {
        replicarDesenho(controller, this.desenho);
    }
}
