package de.stekoe.idss.page;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

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

    private final InfoObjectModel infoObjectModel;

    public SetupPage() {
        infoObjectModel = new InfoObjectModel();

        add(new Label("user.count", infoObjectModel.getObject().userCount));

        List<SystemRole> systemRoles = systemRoleService.findAll();
        add(new Label("role.count", infoObjectModel.getObject().roleCount));
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
                infoObjectModel.detach();
                setResponsePage(getPage());
            }
        });
    }

    class InfoObjectModel extends Model<InfoObject> {

        private InfoObject infoObject = null;

        @Override
        public InfoObject getObject() {
            if(infoObject == null) {
                infoObject = new InfoObject();
                infoObject.userCount = (int) userService.count();
                infoObject.roleCount = (int) systemRoleService.count();
            }

            return infoObject;
        }

        @Override
        public void detach() {
            infoObject = null;
        }
    }

    class InfoObject implements Serializable {
        public int userCount = 0;
        public int roleCount = 0;
    }
}
