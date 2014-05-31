package de.stekoe.idss.page;

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.SystemRoleService;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.setup.DatabaseSetup;

public class SetupPage extends ContainerLayoutPage {
    private static final Logger LOG = Logger.getLogger(SetupPage.class);

    @Inject
    DatabaseSetup databaseSetup;

    @Inject
    UserService userService;

    @Inject
    SystemRoleService systemRoleService;

    @Inject
    CriterionGroupService criterionGroupService;

    public SetupPage() {
        add(new Label("user.count", new Model<Long>(){
            @Override
            public Long getObject() {
                return userService.count();
            };
        }));

        add(new Label("role.count", new Model<Long>(){
            @Override
            public Long getObject() {
                return systemRoleService.count();
            }
        }));

        Model<ArrayList<SystemRole>> systemRolesModel = new Model<ArrayList<SystemRole>>() {
            @Override
            public ArrayList<SystemRole> getObject() {
                return (ArrayList<SystemRole>) systemRoleService.findAll();
            }
        };
        add(new ListView<SystemRole>("role.list", systemRolesModel){
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
                setResponsePage(getPage());
            }
        });
    }
}
