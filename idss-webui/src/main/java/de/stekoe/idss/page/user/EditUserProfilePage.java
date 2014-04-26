/*
 * Copyright 2014 Stephan Koeninger
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

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.Address;
import de.stekoe.idss.model.PhoneNumber;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.page.component.behavior.Placeholder;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.form.AddressField;

@SuppressWarnings("serial")
public class EditUserProfilePage extends AuthUserPage {
    private static final Logger LOG = Logger.getLogger(EditUserProfilePage.class);

    @SpringBean
    private UserService userService;
    private final User user;

    private PhoneNumber phone = new PhoneNumber();
    private PhoneNumber fax = new PhoneNumber();
    private Address address = new Address();

    public EditUserProfilePage(PageParameters parameters) {
        super(parameters);
        user = WebSession.get().getUser();

        Form form = new Form<User>("userprofile") {
            @Override
            protected void onSubmit() {
                try {
                    if(phone.hasNumber()) {
                        user.getProfile().setTelefon(phone);
                    }
                    if(fax.hasNumber()) {
                        user.getProfile().setTelefax(fax);
                    }
                    user.getProfile().setAddress(address);
                    userService.save(user);
                } catch (UserException e) {
                    LOG.error("Error while saving user profile.", e);
                }
            }
        };

        UserProfile profile = user.getProfile();
        if(profile == null) {
            profile = new UserProfile();
            user.setProfile(profile);
        } else {
            phone = (profile.getTelefon() == null) ? new PhoneNumber() : profile.getTelefon();
            fax = (profile.getTelefax() == null) ? new PhoneNumber() : profile.getTelefax();
            address = (profile.getAddress() == null) ? new Address() : profile.getAddress();
        }

        // Name Suffix
        TextField nameSuffix = new TextField("nameSuffix", new PropertyModel(profile, "nameSuffix"));
        nameSuffix.add(new Placeholder(getString("label.name.title")));
        form.add(nameSuffix);

        // Firstname
        TextField firstName = new TextField("firstname", new PropertyModel(profile, "firstname"));
        firstName.add(new Placeholder(getString("label.firstname")));
        form.add(firstName);

        // Surname
        TextField surname = new TextField("surname", new PropertyModel(profile, "surname"));
        surname.add(new Placeholder(getString("label.surname")));
        form.add(surname);

        // Phone
        form.add(new TextField("phone.countryCode", new PropertyModel(phone, "countryCode")));
        form.add(new TextField("phone.areaCode", new PropertyModel(phone, "areaCode")));
        form.add(new TextField("phone.subscriberNumber", new PropertyModel(phone, "subscriberNumber")));

        // Fax
        form.add(new TextField("fax.countryCode", new PropertyModel(fax, "countryCode")));
        form.add(new TextField("fax.areaCode", new PropertyModel(fax, "areaCode")));
        form.add(new TextField("fax.subscriberNumber", new PropertyModel(fax, "subscriberNumber")));

        // Website
        TextField website = new TextField("website", new PropertyModel(profile, "website"));
        website.add(new Placeholder(getString("label.website")));
        form.add(website);

        // Address
        form.add(new AddressField("address", new PropertyModel(profile, "address")));

        add(form);
    }
}
