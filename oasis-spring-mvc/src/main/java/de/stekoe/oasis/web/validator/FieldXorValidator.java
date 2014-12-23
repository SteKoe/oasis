package de.stekoe.oasis.web.validator;


import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class FieldXorValidator implements ConstraintValidator<FieldXor, Object> {
    private String firstFieldName;
    private String secondFieldName;

    public void initialize(final FieldXor constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(firstFieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        Object firstObj = null;
        Object secondObj = null;

        try {
            firstObj = getValueFirstField(value, firstFieldName);
            secondObj = getValueFirstField(value, secondFieldName);
        } catch (final Exception ignore) {
            // Ignore but fail!
            return false;
        }

        return checkIsValid(firstObj, secondObj);
    }

    boolean checkIsValid(Object firstObj, Object secondObj) {
        boolean a = propertHasValue(firstObj);
        boolean b = propertHasValue(secondObj);

        if(a == true && b == false || a == false && b == true) {
            return true;
        }

        if(a == true && b == true || a == false && b == false) {
            return false;
        }

        return false;
    }

    String getValueFirstField(Object value, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return BeanUtils.getProperty(value, fieldName);
    }

    private boolean propertHasValue(Object property) {
        if(property == null) {
            return false;
        }

        if(property instanceof String && ((String) property).trim().isEmpty()) {
            return false;
        }

        return true;
    }
}