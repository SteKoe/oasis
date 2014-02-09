package de.stekoe.idss.page;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.setup.DatabaseSetup;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SetupPage extends LayoutPage {
    @SpringBean
    private DatabaseSetup databaseSetup;

    @SpringBean
    private UserService userService;

    public SetupPage() {
        addInstallExampleUserLink();
    }

    private void addInstallExampleUserLink() {
        final User byUsername = userService.findByUsername("hans.wurst");
        final Link installExampleData = new Link("link.install.example.user") {
            @Override
            public void onClick() {
                databaseSetup.installSampleUser();
                databaseSetup.installSampleProject();
                setResponsePage(getPage());
            }
        };
        installExampleData.setVisible(byUsername == null);
        add(installExampleData);
    }
}
