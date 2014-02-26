package de.stekoe.idss.wicket;

import de.stekoe.idss.model.enums.L10NEnum;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.util.string.Strings;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class EnumChoiceRenderer<T extends Enum<T> & L10NEnum> implements IChoiceRenderer<T> {
    private static final long serialVersionUID = 201402281129L;

    @Override
    public final Object getDisplayValue(T object) {
        final String key = resourceKey(object);
        final String value = Application.get().getResourceSettings().getLocalizer().getString(key, null);
        return postprocess(value);
    }

    protected String resourceKey(T object) {
        return object.getKey();
    }

    protected CharSequence postprocess(String value) {
        return Strings.escapeMarkup(value);
    }

    @Override
    public String getIdValue(T object, int index) {
        return object.name();
    }
}
