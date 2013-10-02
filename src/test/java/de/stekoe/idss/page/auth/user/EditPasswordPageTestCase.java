package de.stekoe.idss.page.auth.user;

import java.util.Locale;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.page.TestIDSSApplication;
import de.stekoe.idss.page.UserAuthUser;

public class EditPasswordPageTestCase {

    private static final String NEWPASSWORD = "i am your new password!";
    private static final String NEWEMAIL = "new_mail@example.com";
    private static final String NEWEMAIL_INVALID = "#@%^%#$@#$@#.com";

    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new TestIDSSApplication());
        getSession().setUser(new UserAuthUser());
        getSession().setLocale(Locale.GERMAN);
    }

    private IDSSSession getSession() {
        return (IDSSSession)tester.getSession();
    }

    @Test
    public void currentPasswordRequired() throws Exception {
        tester.startPage(EditPasswordPage.class);
        tester.assertNoErrorMessage();

        FormTester formTester = tester.newFormTester("editPassword");
        formTester.submit();

        tester.assertRenderedPage(EditPasswordPage.class);
        tester.assertErrorMessages("Bitte tragen Sie einen Wert im Feld 'Aktuelles Passwort' ein.");
    }

    @Test
    public void currentPasswortCorrect() throws Exception {
        tester.startPage(EditPasswordPage.class);
        tester.assertNoErrorMessage();

        FormTester formTester = tester.newFormTester("editPassword");
        formTester.setValue("currentPasswordControlGroup:currentPasswordControlGroup_body:currentPassword", UserAuthUser.PASSWORD);
        formTester.submit();

        tester.assertRenderedPage(EditPasswordPage.class);
        tester.assertNoErrorMessage();
    }

    @Test
    public void currentPasswortInvalid() throws Exception {
        tester.startPage(EditPasswordPage.class);
        tester.assertNoErrorMessage();

        FormTester formTester = tester.newFormTester("editPassword");
        formTester.setValue("currentPasswordControlGroup:currentPasswordControlGroup_body:currentPassword", UserAuthUser.PASSWORD+"iambullshit");
        formTester.submit();

        tester.assertRenderedPage(EditPasswordPage.class);
        tester.assertErrorMessages("Das aktuelle Passwort ist falsch!");
    }

    @Test
    public void changePassword() throws Exception {
        tester.startPage(EditPasswordPage.class);

        FormTester formTester = tester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordControlGroup:currentPasswordControlGroup_body:currentPassword", UserAuthUser.PASSWORD);

        formTester.setValue("newPasswordControlGroup:newPasswordControlGroup_body:newPassword", NEWPASSWORD);
        formTester.setValue("newPasswordConfirmControlGroup:newPasswordConfirmControlGroup_body:newPasswordConfirm", NEWPASSWORD);
        formTester.submit();

        String currentNewPassword = getSession().getUser().getPassword();
        Assert.assertTrue(BCrypt.checkpw(NEWPASSWORD, currentNewPassword));

        tester.assertInfoMessages("Sie haben ihr Passwort erfolgreich geändert!");
        tester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    public void newPasswordsAreNotEqual() throws Exception {
        tester.startPage(EditPasswordPage.class);

        FormTester formTester = tester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordControlGroup:currentPasswordControlGroup_body:currentPassword", UserAuthUser.PASSWORD);

        formTester.setValue("newPasswordControlGroup:newPasswordControlGroup_body:newPassword", NEWPASSWORD);
        formTester.setValue("newPasswordConfirmControlGroup:newPasswordConfirmControlGroup_body:newPasswordConfirm", NEWPASSWORD+"dumbass");
        formTester.submit();

        String currentNewPassword = getSession().getUser().getPassword();
        Assert.assertFalse(BCrypt.checkpw(NEWPASSWORD, currentNewPassword));

        tester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    public void changeEmail() throws Exception {
        tester.startPage(EditPasswordPage.class);

        FormTester formTester = tester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordControlGroup:currentPasswordControlGroup_body:currentPassword", UserAuthUser.PASSWORD);

        formTester.setValue("newEmailControlGroup:newEmailControlGroup_body:newEmail", NEWEMAIL);
        formTester.setValue("newEmailConfirmControlGroup:newEmailConfirmControlGroup_body:newEmailConfirm", NEWEMAIL);
        formTester.submit();

        String currentNewEmail = getSession().getUser().getEmail();
        Assert.assertTrue(currentNewEmail.equals(NEWEMAIL));

        tester.assertInfoMessages("Sie haben ihre E-Mail-Adresse erfolgreich geändert!");
        tester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    public void newEmailsAreNotEqual() throws Exception {
        tester.startPage(EditPasswordPage.class);

        FormTester formTester = tester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordControlGroup:currentPasswordControlGroup_body:currentPassword", UserAuthUser.PASSWORD);

        formTester.setValue("newEmailControlGroup:newEmailControlGroup_body:newEmail", NEWEMAIL);
        formTester.setValue("newEmailConfirmControlGroup:newEmailConfirmControlGroup_body:newEmailConfirm", NEWEMAIL+"imakeyoubullshit");
        formTester.submit();

        String currentNewEmail = getSession().getUser().getEmail();
        Assert.assertFalse(currentNewEmail.equals(NEWEMAIL));

        tester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    public void newEmailsAreInvalidEmailAddresses() throws Exception {
        tester.startPage(EditPasswordPage.class);

        FormTester formTester = tester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordControlGroup:currentPasswordControlGroup_body:currentPassword", UserAuthUser.PASSWORD);

        formTester.setValue("newEmailControlGroup:newEmailControlGroup_body:newEmail", NEWEMAIL_INVALID);
        formTester.setValue("newEmailConfirmControlGroup:newEmailConfirmControlGroup_body:newEmailConfirm", NEWEMAIL_INVALID);
        formTester.submit();

        String currentNewEmail = getSession().getUser().getEmail();
        Assert.assertFalse(currentNewEmail.equals(NEWEMAIL));

        tester.assertRenderedPage(EditPasswordPage.class);
    }
}
