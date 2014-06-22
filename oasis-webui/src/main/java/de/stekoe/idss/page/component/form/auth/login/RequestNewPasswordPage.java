package de.stekoe.idss.page.component.form.auth.login;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.OASISWebApplication;
import de.stekoe.idss.mail.template.ResetPasswordMailTemplate;
import de.stekoe.idss.model.IDGenerator;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.page.ContainerLayoutPage;
import de.stekoe.idss.page.component.behavior.Placeholder;
import de.stekoe.idss.service.MailService;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;

public class RequestNewPasswordPage extends ContainerLayoutPage {
    private static final Logger LOG = Logger.getLogger(RequestNewPasswordPage.class);

    @Inject
    UserService userService;

    @Inject
    MailService mailService;

    private String email;

    public RequestNewPasswordPage() {
        Form form = new Form("form.password.reset") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                User user = userService.findByEmail(email);
                if(user != null) {
                    user.setUserStatus(UserStatus.RESET_PASSWORD);
                    user.setActivationKey(IDGenerator.createId());

                    try {
                        userService.save(user);

                        Map<String, String> vars = new HashMap<String, String>();
                        vars.put("name", user.getUsername());

                        String resetPasswordLinkURL = (String) getRequestCycle().urlFor(ResetPasswordPage.class, new PageParameters().add("token", user.getActivationKey()));
                        resetPasswordLinkURL = RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(resetPasswordLinkURL));
                        vars.put("resetPasswordLink", resetPasswordLinkURL);

                        String body = new ResetPasswordMailTemplate().setVariables(vars);

                        mailService.sendMail(user.getEmail(), getString("email.subject.resetpassword"), body);
                        success(getString("message.password.reset.success"));
                        setResponsePage(OASISWebApplication.get().getHomePage());
                    } catch (UserException e) {
                        LOG.error("Error while resetting password of user.", e);
                    }
                }
            }
        };
        add(form);

        TextField<String> emailField = new TextField<String>("email", new PropertyModel<String>(this, "email"));
        emailField.add(new Placeholder(getString("label.email")));
        form.add(emailField);
    }
}
