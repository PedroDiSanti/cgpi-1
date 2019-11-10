package cgpi.vtec;

import cgpi.controller.AbstractController;
import cgpi.figuras.model.Desenho;
import cgpi.vtec.annotation.OnMouseClick;
import cgpi.vtec.annotation.TextField;
import cgpi.vtec.events.AbstractDesenhoEvent;
import cgpi.vtec.exception.VFXMLLoaderException;
import javafx.scene.Node;
import javafx.util.Builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author vitor.alves
 */
public class ControllerBuilder implements Builder<AbstractController<?>> {

    private static final String COMPONENTS_FIELD = "components";

    private static final String PARENT_FIELD = "parent";

    private AbstractController controller;

    private AbstractController parentController;

    private Object model;

    private Set<Map.Entry<String, Object>> entries;

    public ControllerBuilder(AbstractController<?> controller, Set<Map.Entry<String, Object>> entries) {
        this.controller = controller;
        this.entries = entries;
    }

    public AbstractController getParentController() {
        return parentController;
    }

    public void setParentController(AbstractController parentController) {
        this.parentController = parentController;
    }

    @Override
    public AbstractController<?> build() {
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
            constructor = event.getConstructor(AbstractController.class);
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
    }

    private Field getField(String fieldName) throws VFXMLLoaderException {
        Class<?> aClass = this.controller.getClass();
        Field field = null;
        while (field == null && aClass != null) {
            aClass = aClass.getSuperclass();
            if (aClass != null && containsField(aClass, fieldName)) {
                try {
                    field = aClass.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    throw new VFXMLLoaderException("No AbstractController instance controller.", e);
                }
            }
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
