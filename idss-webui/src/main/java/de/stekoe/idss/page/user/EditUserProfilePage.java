package de.stekoe.idss.page.user;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.*;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.session.WebSession;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class EditUserProfilePage extends AuthUserPage {
    private static final Logger LOG = Logger.getLogger(EditUserProfilePage.class);

    @SpringBean
    private UserService userService;
    private User user;

    public EditUserProfilePage(PageParameters parameters) {
        super(parameters);
        user = WebSession.get().getUser();

        Form form = new Form<User>("userprofile") {
            @Override
            protected void onSubmit() {
                    try {
                        userService.save(user);
                    } catch (UsernameAlreadyInUseException e) {
                        LOG.error("A user tried to register with existing username: " + user.getUsername());
                    } catch (EmailAddressAlreadyInUseException e) {
                        LOG.error("A user tried to register with existing email: " + user.getUsername());
                    }
            }
        };

        form.add(new BookmarkablePageLink("changePasswordOrEmailLink", EditPasswordPage.class));

        final UserProfile profile = user.getProfile();
        form.add(new TextField("firstname", new PropertyModel(profile, "firstname")));
        form.add(new TextField("surname", new PropertyModel(profile, "surname")));
        form.add(createDateTextField());

        add(form);
    }

    private DateTextField createDateTextField() {
        DateTextFieldConfig config = new DateTextFieldConfig();
        config.autoClose(true);
        config.withLanguage(getSession().getLocale().getLanguage());
        config.showTodayButton(DateTextFieldConfig.TodayButton.TRUE);
        config.withFormat(getDateFormat());
        return new DateTextField("birthday", new PropertyModel(user.getProfile(), "birthdate"), config);
    }

    private java.lang.String getDateFormat() {
        Locale currentLocale = getSession().getLocale();
        final SimpleDateFormat dateInstance = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, currentLocale);
        return dateInstance.toPattern();
    }
}
