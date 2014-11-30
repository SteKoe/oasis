package de.stekoe.oasis.conf;

import de.stekoe.oasis.init.WebInitializer;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "de.stekoe.oasis")
@Import({ WebInitializer.class, DispatcherConfig.class, SecurityConfig.class})
@ContextConfiguration(locations = {"classpath:services/BeanLocations.xml"})
public class AppConfiguration {
    public Map<Locale, String> getLocales() {
        Map<Locale, String> locales = new HashMap<>();
        locales.put(Locale.GERMAN, "Deutsch");
        locales.put(Locale.ENGLISH, "English");
        locales.put(new Locale("pl"), "Polski");
        locales.put(Locale.ITALIAN, "Italiano");
        locales.put(new Locale("es"), "Español");
        return locales;
    }
}