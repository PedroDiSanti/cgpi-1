package cgpi.vtec.controllers;

import cgpi.controller.MainController;
import cgpi.figuras.model.Desenho;
import cgpi.vtec.SceneManager;
import cgpi.vtec.annotation.OnMouseClick;
import cgpi.vtec.annotation.TextField;
import cgpi.vtec.events.AbstractDesenhoEvent;
import cgpi.vtec.events.CropEvent;
import cgpi.vtec.exception.VFXMLLoaderException;
import cgpi.vtec.models.TextFieldListner;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Inicializa e configura o controller de um xml
 *
 * @author vitor.alves
 */
public class ControllerBuilder implements Builder<AbstractDesenhoController<?>> {

    private static final String COMPONENTS_FIELD = "components";

    private static final String PARENT_FIELD = "parent";

    private AbstractDesenhoController controller;

    private AbstractDesenhoController parentController;

    private final Stage stage;

    private Object model;

    private Set<Map.Entry<String, Object>> entries;

    public ControllerBuilder(AbstractDesenhoController<?> controller, Set<Map.Entry<String, Object>> entries, Stage stage) {
        this.controller = controller;
        this.entries = entries;
        this.stage = stage;
    }

    public void setParentController(AbstractDesenhoController parentController) {
        this.parentController = parentController;
    }

    @Override
    public AbstractDesenhoController<?> build() {
        try {
            this.setUpControllerFields();
            this.setUpControllerModel();
        } catch (VFXMLLoaderException e) {
            e.printStackTrace();
        }

        if (this.controller instanceof Inicializavel) {
            ((Inicializavel) this.controller).inicialize();
        }

        return this.controller;
    }

    private void setUpControllerModel() throws VFXMLLoaderException {
        this.model = this.controller.getModel();
        if (this.model != null) {
            for (Method declaredMethod : model.getClass().getDeclaredMethods()) {
                if (declaredMethod.isAnnotationPresent(OnMouseClick.class)) {
                    this.createEventOnMouseClick(model, declaredMethod, declaredMethod.getAnnotation(OnMouseClick.class));
                } else if (declaredMethod.isAnnotationPresent(TextField.class)) {
                    this.associateModelToComponent(declaredMethod, declaredMethod.getAnnotation(TextField.class));
                }
            }
        }
    }

    private void associateModelToComponent(Method declaredMethod, TextField annotation) {
        String value = annotation.value();
        javafx.scene.control.TextField node = (javafx.scene.control.TextField) controller.get(value);
        node.textProperty().addListener(new TextFieldListner(declaredMethod, model));
        try {
            declaredMethod.invoke(model, node.getText());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void createEventOnMouseClick(Object model, Method declaredMethod, OnMouseClick annotation) throws VFXMLLoaderException {
        Class<? extends AbstractDesenhoEvent> event = annotation.event();
        String value = annotation.value();

        Node node = controller.get(value);
        if (node == null) {
            throw new VFXMLLoaderException("No component with name of: " + value);
        }
        Constructor<? extends AbstractDesenhoEvent> constructor;
        Desenho desenho;
        try {
            constructor = event.getConstructor(AbstractDesenhoController.class);
            AbstractDesenhoEvent desenhoEvent = constructor.newInstance(controller);
            desenho = desenhoEvent.getDesenho();
            node.setOnMouseClicked(desenhoEvent);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new VFXMLLoaderException("Event not of type AbstractDesenhoEvent", e);
        }
        try {
            declaredMethod.invoke(model, desenho);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new VFXMLLoaderException("Event no on seter method.", e);
        }
    }

    private void setUpControllerFields() throws VFXMLLoaderException {
        Map<String, Node> components = new HashMap<>();
        Object value;
        for (Map.Entry<String, Object> entry : entries) {
            value = entry.getValue();
            if (value instanceof Node) {
                components.put(entry.getKey(), (Node) value);
            }
        }

        Field field = getField(COMPONENTS_FIELD);
        this.setField(field, controller, components);
        if (this.parentController != null) {
            field = getField(PARENT_FIELD);
            this.setField(field, controller, parentController);
        }

        if (this.controller instanceof MainController) {
            field = getField("sceneManager");
            this.setField(field, controller, new SceneManager(stage));
        }

        Node node = components.get("cortar");
        if (node != null) {
            node.setOnMouseClicked(new CropEvent(controller));
        }
    }

    private Field getField(String fieldName) throws VFXMLLoaderException {
        Class<?> aClass = this.controller.getClass();
        Field field = null;
        while (field == null && aClass != null) {
            if (aClass != null && containsField(aClass, fieldName)) {
                try {
                    field = aClass.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    throw new VFXMLLoaderException("No AbstractController instance controller.", e);
                }
            }
            aClass = aClass.getSuperclass();
        }
        if (aClass == null) {
            throw new VFXMLLoaderException("No AbstractController instance controller.");
        }
        return field;
    }

    private boolean containsField(Class<?> aClass, String fieldName) {
        try {
            aClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }

    private void setField(Field field, Object object, Object value) throws VFXMLLoaderException {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new VFXMLLoaderException("No AbstractController instance controller.", e);
        }
    }
}
