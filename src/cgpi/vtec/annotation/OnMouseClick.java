package cgpi.vtec.annotation;

import cgpi.vtec.events.AbstractDesenhoEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vitor.alves
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnMouseClick {

    String value();

    Class<? extends AbstractDesenhoEvent> event();
}
