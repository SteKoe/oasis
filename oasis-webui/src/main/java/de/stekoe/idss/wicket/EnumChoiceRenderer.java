package de.stekoe.idss.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.util.string.Strings;

import de.stekoe.idss.model.L10NEnum;

public class EnumChoiceRenderer<T extends Enum<T> & L10NEnum> implements IChoiceRenderer<T> {
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
