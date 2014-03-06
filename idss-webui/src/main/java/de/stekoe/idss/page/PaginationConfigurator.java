package de.stekoe.idss.page;

import java.util.HashMap;
import java.util.Map;

public class PaginationConfigurator {
    private int defaultValue = 25;
    private Map<Class, Integer> values = new HashMap<Class, Integer>();

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<Class, Integer> getValues() {
        return values;
    }

    public void setValues(Map<Class, Integer> values) {
        this.values = values;
    }

    public int getValueFor(Class homePageClass) {
        final Integer valueForClazz = getValues().get(homePageClass);
        if(valueForClazz == null) {
            return getDefaultValue();
        } else {
            return valueForClazz;
        }
    }
}
