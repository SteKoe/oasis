package de.stekoe.idss.component.navigation.language;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.IDSSApplication;


@SuppressWarnings("serial")
public class LanguageSwitcher extends Panel {

    private final List<Locale> languages = new ArrayList<Locale>();

    public LanguageSwitcher(String id) {
        super(id);

        if(getLanguages().size() <= 1) {
            setVisible(false);
        } else {
            createLanguageSwitcher();
        }
    }

    private void createLanguageSwitcher() {
        ListView<Locale> loop = new ListView<Locale>("languages", getLanguages()) {

            @Override
            protected void populateItem(final ListItem<Locale> item) {
                final Locale languageKey = item.getModelObject();

                WebMarkupContainer li = new WebMarkupContainer("languageItem");
                Locale currentLocale = IDSSSession.get().getLocale();
                boolean languageEqual = languageKey.getLanguage().equals(currentLocale.getLanguage());
                if(languageEqual) {
                    li.add(new AttributeModifier("class", "active"));
                }
                item.add(li);

                Link link = new Link("languageLink") {
                    @Override
                    public void onClick() {
                        Locale key = languageKey;
                        IDSSSession.get().setLocale(key);
                    }
                };
                link.add(new Label("languageName", Model.of(item.getModelObject())));
                li.add(link);
            }
        };
        loop.setRenderBodyOnly(true);

        add(loop);
    }

    private List<Locale> getLanguages() {
        List<Locale> languages = new ArrayList<Locale>();

        for(Locale l : IDSSApplication.LANGUAGES) {
            languages.add(l);
        }

        return languages;
    }

}
