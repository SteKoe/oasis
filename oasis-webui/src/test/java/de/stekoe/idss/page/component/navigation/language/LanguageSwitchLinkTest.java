package de.stekoe.idss.page.component.navigation.language;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.Test;

import de.stekoe.idss.AbstractWicketApplicationTester;


public class LanguageSwitchLinkTest extends AbstractWicketApplicationTester {
    @Test
    public void getLocaleString() throws Exception {
        LanguageSwitchLink link = new LanguageSwitchLink("wicketId", Locale.GERMAN);
        assertThat(link.getLanguageKey(), equalTo("de"));
    }
}
