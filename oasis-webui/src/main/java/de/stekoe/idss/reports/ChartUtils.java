package de.stekoe.idss.reports;

import org.apache.wicket.Localizer;

import de.stekoe.idss.OASISWebApplication;

public class ChartUtils {

    private ChartUtils() {
        //NOP
    }

    public static String getString(String key) {
        Localizer localizer = OASISWebApplication.get().getResourceSettings().getLocalizer();
        return localizer.getString(key, null);
    }
}
