package de.stekoe.idss;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.resource.loader.IStringResourceLoader;

public class DatabaseResourceLoader implements IStringResourceLoader {

    @Override
    public String loadStringResource(Class<?> clazz, String key, Locale locale, String style, String variation) {
        return null;
    }

    @Override
    public String loadStringResource(Component component, String key, Locale locale, String style, String variation) {
        return null;
    }

}
