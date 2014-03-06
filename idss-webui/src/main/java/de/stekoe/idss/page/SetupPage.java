/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
