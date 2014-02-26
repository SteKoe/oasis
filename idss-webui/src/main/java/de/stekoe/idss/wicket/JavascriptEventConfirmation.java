package de.stekoe.idss.wicket;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class JavascriptEventConfirmation extends AttributeModifier {

    /**
     * @param event Name of JS event like "onClick".
     * @param msg   Message to be shown by JS confirm.
     */
    public JavascriptEventConfirmation(String event, String msg) {
        super(event, new Model<String>(msg));
    }

    @Override
    protected String newValue(final String currentValue, final String replacementValue) {
        String prefix = "var conf = confirm('" + replacementValue + "'); " +
                "if (!conf) return false; ";
        String result = prefix;
        if (currentValue != null) {
            result = prefix + currentValue;
        }
        return result;
    }
}