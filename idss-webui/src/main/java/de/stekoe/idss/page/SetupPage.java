package de.stekoe.idss.page;

import javax.inject.Inject;

import org.apache.wicket.markup.html.link.Link;

import de.stekoe.idss.setup.DatabaseSetup;

public class SetupPage extends LayoutPage {

    @Inject
    DatabaseSetup databaseSetup;

    public SetupPage() {
        add(new Link("run.setup") {
            @Override
            public void onClick() {
                databaseSetup.run();
            }

        });
    }
}
