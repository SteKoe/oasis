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

package de.stekoe.idss.page;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.OASISWebApplication;
import de.stekoe.idss.service.MailService;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;

@SuppressWarnings("serial")
public class ContactPage extends LayoutPage {
    private static final Logger LOG = Logger.getLogger(ContactPage.class);

    private final ContactMail contactMailObject = new ContactMail();

    @Inject
    private MailService itsMailService;

    public ContactPage() {

        StatelessForm<ContactMail> contactForm = new StatelessForm<ContactMail>("form.contact", new Model<ContactMail>(contactMailObject)) {
            @Override
            protected void onSubmit() {
                ContactMail contactMail = getModelObject();

                StringBuilder sbTo = new StringBuilder();
                sbTo.append(contactMail.getName());
                sbTo.append("<");
                sbTo.append(contactMail.getEmail());
                sbTo.append(">");

                String concactMail = OASISWebApplication.get().getServletContext().getInitParameter("email.noreply");
                itsMailService.sendMail(concactMail, sbTo.toString(), "Mail via ContactForm", contactMail.getBody());

                success("[The mail has been sent!]");
                setResponsePage(ContactPage.class);
            }
        };
        add(contactForm);

        TextField nameTextField = new TextField("form.contact.name.field", new PropertyModel<String>(contactMailObject, "name"));
        nameTextField.setLabel(new ResourceModel("label.name"));
        nameTextField.setRequired(true);
        contactForm.add(new FormGroup("form.contact.name.group").add(nameTextField));

        EmailTextField emailTextField = new EmailTextField("form.contact.email.field", new PropertyModel<String>(contactMailObject, "email"));
        emailTextField.setLabel(new ResourceModel("label.email"));
        emailTextField.setRequired(true);
        contactForm.add(new FormGroup("form.contact.email.group").add(emailTextField));

        TextArea<String> bodyTextArea = new TextArea<String>("form.contact.body.field", new PropertyModel<String>(contactMailObject, "body"));
        bodyTextArea.setLabel(new ResourceModel("label.email"));
        bodyTextArea.setRequired(true);
        bodyTextArea.add(new AttributeModifier("rows", 15));
        contactForm.add(new FormGroup("form.contact.body.group").add(bodyTextArea));

        contactForm.add(new MarkRequiredFieldsBehavior());
    }

    private class ContactMail implements Serializable {
        private String name;
        private String email;
        private String body;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getBody() {
            return body;
        }
        public void setBody(String body) {
            this.body = body;
        }
    }
}
