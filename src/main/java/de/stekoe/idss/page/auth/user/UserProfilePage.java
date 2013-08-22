package de.stekoe.idss.page.auth.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IUserService;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class UserProfilePage extends AuthUserPage {

    @SpringBean
    private IUserService userService;

    private final User user = getSession().getUser();

    /**
     * Construct.
     */
    public UserProfilePage() {
        final User user = getSession().getUser();

        Form form = new Form<User>("userprofile") {
            @Override
            protected void onSubmit() {
                userService.update(user);
            }
        };

        form.add(new BookmarkablePageLink("changePasswordOrEmailLink", EditPasswordPage.class));
        form.add(new TextField("firstname", new PropertyModel(user
                .getUserProfile(), "firstname")));
        form.add(new TextField("surename", new PropertyModel(user
                .getUserProfile(), "surename")));
        form.add(createDateTextField());

        add(form);
    }

    private DateTextField createDateTextField() {
        DateTextFieldConfig config = new DateTextFieldConfig();
        config.autoClose(true);
        config.withLanguage(getSession().getLocale().getLanguage());
        config.showTodayButton(true);
        config.withFormat(getDateFormat());
        return new DateTextField("birthday", new PropertyModel(user.getUserProfile(), "birthdate"), config);
    }

    private String getDateFormat() {
        Locale currentLocale = getSession().getLocale();
        final SimpleDateFormat dateInstance = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
        final String pattern = dateInstance.toPattern();
        return pattern;
    }
}
