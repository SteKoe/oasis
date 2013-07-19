package de.stekoe.idss.component.form.registration;

import java.util.List;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class UniqueContentValidator implements IValidator<String> {

    private List<String> checkers;

    public UniqueContentValidator(List<String> checkers) {
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
