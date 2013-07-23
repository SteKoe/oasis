package de.stekoe.idss.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import de.stekoe.idss.IDSSSession;


@SuppressWarnings("serial")
public class LanguageSwitcher extends Panel {

    private final List<Locale> languages = new ArrayList<Locale>();

    public LanguageSwitcher(String id) {
        super(id);

        setLanguages();

        ListView<Locale> loop = new ListView<Locale>("languages", getLanguages()) {

            @Override
            protected void populateItem(final ListItem<Locale> item) {
                Link link = new Link("language") {
                    @Override
                    public void onClick() {
                        Locale key = item.getModelObject();
                        IDSSSession.get().setLocale(key);
                    }
                };

                link.add(new Label("languageName", Model.of(item.getModelObject())));

                item.add(link);
            }
        };
        loop.setRenderBodyOnly(true);

        add(loop);
    }

    private void setLanguages() {
        languages.add(Locale.ENGLISH);
        languages.add(Locale.GERMAN);
    }

    private List<Locale> getLanguages() {
        return languages;
    }

}
