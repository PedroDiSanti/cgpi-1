package cgpi.vtec.models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author vitor.alves
 */
public class TextFieldListner implements ChangeListener<String> {

    private Method method;

    private Object model;

    public TextFieldListner(Method declaredMethod, Object model) {
        this.method = declaredMethod;
        this.model = model;
    }


    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            method.invoke(model, newValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
