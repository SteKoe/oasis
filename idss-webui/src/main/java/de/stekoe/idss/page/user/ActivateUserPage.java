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

package de.stekoe.idss.page.user;

import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.page.LayoutPage;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;

import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class ActivateUserPage extends LayoutPage {
    private static final Logger LOG = Logger.getLogger(ActivateUserPage.class);

    @SpringBean
    private UserService itsUserManager;

    private String itsActivationCode = null;

    public ActivateUserPage(PageParameters aParams) {
        super(aParams);

        extractActivationKeyFromPageParameters(aParams);
        if (!itsActivationCode.isEmpty()) {
            User userToActivate = getUserToActivate();
            if (userToActivate != null) {
                activateUser(userToActivate);
            }
        }
    }

    private User getUserToActivate() {
        return itsUserManager.findByActivationCode(itsActivationCode);
    }

    private void extractActivationKeyFromPageParameters(PageParameters aParams) {
        StringValue activationCode = aParams.get(0);
        if (!activationCode.isEmpty()) {
            this.itsActivationCode = activationCode.toString();
        }
    }

    private void activateUser(User aUserToActivate) {
        aUserToActivate.setActivationKey(null);
        aUserToActivate.setUserStatus(UserStatus.ACTIVATED);
        try {
            itsUserManager.save(aUserToActivate);
            setResponsePage(LoginPage.class);
        } catch (UserException e) {
            LOG.error("Error while activating user " + aUserToActivate.getUsername() + "!", e);
        }
    }
}
