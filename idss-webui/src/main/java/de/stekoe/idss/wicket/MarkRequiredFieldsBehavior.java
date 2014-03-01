package de.stekoe.idss.wicket;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotNull;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MarkRequiredFieldsBehavior extends Behavior {

    private static final Logger LOG = Logger.getLogger(MarkRequiredFieldsBehavior.class);

    @Override
    public void bind(Component component) {
        if(!(component instanceof Form)) {
            throw new IllegalArgumentException("Argument component has to be of type Form!");
        }

        final Form form = (Form) component;
        final ComponentHierarchyIterator components = form.visitChildren();
        while(components.hasNext()) {
            final Component next = components.next();
            if(next instanceof FormComponent) {
                FormComponent formComponent = (FormComponent) next;
                if(formComponent.isRequired() || checkModelAnnotations(formComponent)) {
                    markFieldRequired(formComponent);
                }
            }
        }
    }

    private boolean checkModelAnnotations(FormComponent aNext) {
        final String fieldName = aNext.getId();
        final Class classObject = aNext.getForm().getModelObject().getClass();

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

    private void markFieldRequired(FormComponent aComponent) {
        final IModel<String> label = aComponent.getLabel();
        if(label != null) {
            final String object = label.getObject();
            label.setObject(object + "*");
        }
    }
}