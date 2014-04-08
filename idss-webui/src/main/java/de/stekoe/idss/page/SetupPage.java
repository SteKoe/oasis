package de.stekoe.idss.page;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.service.SystemRoleService;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.setup.DatabaseSetup;

public class SetupPage extends LayoutPage {

    @Inject
    DatabaseSetup databaseSetup;

    @Inject
    UserService userService;

    @Inject
    SystemRoleService systemRoleService;

    public SetupPage() {

        add(new Label("user.count", userService.getAllUsers().size()));

        List<SystemRole> systemRoles = systemRoleService.findAll();
        add(new Label("role.count", systemRoles.size()));
        add(new ListView<SystemRole>("role.list", systemRoles){
            @Override
            protected void populateItem(ListItem<SystemRole> item) {
                item.add(new Label("role.id", item.getModelObject().getId()));
                item.add(new Label("role.name", item.getModelObject().getName()));
            }

        });

        add(new Link("run.setup") {
            @Override
            public void onClick() {
                databaseSetup.run();
            }
        });
    }
}
