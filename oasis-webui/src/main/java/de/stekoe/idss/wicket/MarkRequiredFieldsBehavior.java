package de.stekoe.idss.wicket;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.springframework.beans.factory.annotation.Required;

public class MarkRequiredFieldsBehavior extends Behavior {

    private static final Logger LOG = Logger.getLogger(MarkRequiredFieldsBehavior.class);

    @Override
    public void bind(Component component) {
        if(!(component instanceof Form)) {
            throw new IllegalArgumentException("Argument component has to be of type Form!");
        }

        final Form form = (Form) component;

        form.visitChildren(FormComponent.class, new IVisitor<FormComponent, Void>() {
            @Override
            public void component(FormComponent object, IVisit<Void> visit) {
                if(object.isRequired() || checkModelAnnotations(object)) {
                    markFieldRequired(object);
                }
            }
        });
    }

    private boolean checkModelAnnotations(FormComponent formComponent) {
        final Object modelObject = formComponent.getForm().getModelObject();
        if(modelObject == null) {
            return false;
        }

        final String fieldName = formComponent.getId();
        final Class classObject = modelObject.getClass();

        try {
            final Method getter = new PropertyDescriptor(fieldName, classObject).getReadMethod();
            final List<Annotation> annotations = Arrays.asList(getter.getDeclaredAnnotations());
            for(Annotation annotation : annotations) {
                if(annotation instanceof NotNull || annotation instanceof Required) {
                    return true;
                }
            }

            return false;
        } catch (IntrospectionException e) {
            LOG.error("Failed to check if field is required by reflection!", e);
        }

        return false;
    }

    private void markFieldRequired(FormComponent formComponent) {
        final IModel<String> label = formComponent.getLabel();
        if(label != null) {
            final String object = label.getObject();
            label.setObject(object + "*");
        }
    }
}
