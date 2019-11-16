package cgpi.vtec.events;

import cgpi.vtec.controllers.AbstractDesenhoController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author vitor.alves
 */
public class CropEvent implements EventHandler<MouseEvent> {

    private final AbstractDesenhoController controller;
    private Stage primaryStage;
    private Canvas canvas;
    private RubberBandSelection rubberBandSelection;

    public CropEvent(AbstractDesenhoController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(MouseEvent newevent) {
        this.primaryStage = new Stage();

        primaryStage.setTitle("Cortar imagem");
        BorderPane root = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        Group imageLayer = new Group();

        canvas = (Canvas) controller.get("canvas");

        imageLayer.getChildren().add(canvas);
        scrollPane.setContent(imageLayer);
        root.setCenter(scrollPane);

        rubberBandSelection = new RubberBandSelection(imageLayer);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem cropMenuItem = new MenuItem("Cortar imagem");

        cropMenuItem.setOnAction(e -> {
            Bounds selectionBounds = rubberBandSelection.getBounds();
            System.out.println("Área selecionada: " + selectionBounds);
            crop(selectionBounds);
            primaryStage.close();
            controller.limpe();
        });
        contextMenu.getItems().add(cropMenuItem);

        imageLayer.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                contextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
            }
        });

        primaryStage.setScene(new Scene(root, 512, 384));
        primaryStage.show();
    }

    private void crop(Bounds bounds)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar imagem");

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file == null)
            return;

        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

        WritableImage wi = new WritableImage(width, height);
        canvas.snapshot(parameters, wi);

        BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
        BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(), BufferedImage.OPAQUE);

        Graphics2D graphics = bufImageRGB.createGraphics();
        graphics.drawImage(bufImageARGB, 0, 0, null);

        try {
            ImageIO.write(bufImageRGB, "jpg", file);
            System.out.println("Imagem salva em: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        graphics.dispose();
    }

    public static class RubberBandSelection
    {
        Group group;
        javafx.scene.shape.Rectangle rect;
        final DragContext dragContext = new DragContext();


        private Bounds getBounds()
        {
            return rect.getBoundsInParent();
        }

        private RubberBandSelection(Group group)
        {
            this.group = group;
            rect = new Rectangle(0,0,0,0);

            rect.setStrokeWidth(1);
            rect.setStroke(Color.BLUE);
            rect.setStrokeLineCap(StrokeLineCap.ROUND);
            rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

            group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        }

        EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown())
                    return;

                rect.setX(0);
                rect.setY(0);
                rect.setWidth(0);
                rect.setHeight(0);

                group.getChildren().remove(rect);

                dragContext.mouseAnchorX = event.getX();
                dragContext.mouseAnchorY = event.getY();

                rect.setX(dragContext.mouseAnchorX);
                rect.setY(dragContext.mouseAnchorY);
                rect.setWidth(0);
                rect.setHeight(0);

                group.getChildren().add(rect);

            }
        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown())
                    return;

                double offsetX = event.getX() - dragContext.mouseAnchorX;
                double offsetY = event.getY() - dragContext.mouseAnchorY;

                if (offsetX > 0) {
                    rect.setWidth(offsetX);
                } else {
                    rect.setX(event.getX());
                    rect.setWidth(dragContext.mouseAnchorX - rect.getX());
                }

                if (offsetY > 0) {
                    rect.setHeight(offsetY);
                } else {
                    rect.setY(event.getY());
                    rect.setHeight(dragContext.mouseAnchorY - rect.getY());
                }
            }
        };

        EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
            if(event.isSecondaryButtonDown()) {
                return;
            }
        };

        private static final class DragContext
        {
            double mouseAnchorX;
            double mouseAnchorY;
        }
    }
}
