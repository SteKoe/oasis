package de.stekoe.oasis.conf;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class AppConfiguration {
    public Map<Locale, String> getLocales() {
        Map<Locale, String> locales = new HashMap<>();
        locales.put(Locale.GERMAN, "Deutsch");
        //locales.put(Locale.ENGLISH, "English");
        //locales.put(new Locale("pl"), "Polski");
        //locales.put(Locale.ITALIAN, "Italiano");
        //locales.put(new Locale("es"), "Español");
        return locales;
    }
}