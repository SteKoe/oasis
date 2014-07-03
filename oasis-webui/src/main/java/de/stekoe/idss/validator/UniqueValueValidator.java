package de.stekoe.idss.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.List;

/**
 * @author Stephan Koeninger 
 */
@SuppressWarnings("serial")
public class UniqueValueValidator implements IValidator<String> {

    private final List<String> checkers;

    /**
     * Construct.
     *
     * @param checkers List of Strings which are not allowed to be value of the textfield.
     */
    public UniqueValueValidator(List<String> checkers) {
        this.checkers = checkers;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String fieldValue = validatable.getValue();

        if (checkers.contains(fieldValue)) {
            error(validatable, "notUnique");
        }
    }

    private void error(IValidatable<String> validatable, String errorKey) {
        ValidationError error = new ValidationError();
        error.addKey(getClass().getSimpleName() + "." + errorKey);
        validatable.error(error);
    }
}
