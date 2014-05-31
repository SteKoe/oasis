package de.stekoe.idss.page.user;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.service.AuthService;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;
import java.util.Locale;

public class EditPasswordPageTestCase extends AbstractWicketApplicationTester {

    private static final String NEWPASSWORD = "i am your new password!";
    private static final String NEWEMAIL = "new_mail@example.com";
    private static final String NEWEMAIL_INVALID = "#@%^%#$@#$@#.com";

    @Inject
    private AuthService authService;

    @Before
    public void setUp() {
        getSession().signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        getSession().setLocale(Locale.GERMAN);
    }

    @Test
    @DirtiesContext
    public void currentPasswordRequired() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);
        wicketTester.assertNoErrorMessage();

        FormTester formTester = wicketTester.newFormTester("editPassword");
        formTester.submit();

        wicketTester.assertRenderedPage(EditPasswordPage.class);
        wicketTester.assertErrorMessages("Dieses Feld ist obligatorisch!");
    }

    @Test
    @DirtiesContext
    public void currentPasswortCorrect() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);
        wicketTester.assertNoErrorMessage();

        FormTester formTester = wicketTester.newFormTester("editPassword");
        formTester.setValue("currentPasswordFormGroup:currentPasswordFormGroup_body:currentPassword", TestFactory.USER_PASSWORD);
        formTester.submit();

        wicketTester.assertRenderedPage(EditPasswordPage.class);
        wicketTester.assertNoErrorMessage();
    }

    @Test
    @DirtiesContext
    public void currentPasswortInvalid() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);
        wicketTester.assertNoErrorMessage();

        FormTester formTester = wicketTester.newFormTester("editPassword");
        formTester.setValue("currentPasswordFormGroup:currentPasswordFormGroup_body:currentPassword", TestFactory.USER_PASSWORD + "iambullshit");
        formTester.submit();

        wicketTester.assertRenderedPage(EditPasswordPage.class);
        wicketTester.assertErrorMessages("Das aktuelle Passwort ist falsch!");
    }

    @Test
    @DirtiesContext
    public void changePassword() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);

        FormTester formTester = wicketTester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordFormGroup:currentPasswordFormGroup_body:currentPassword", TestFactory.USER_PASSWORD);

        formTester.setValue("newPasswordFormGroup:newPasswordFormGroup_body:newPassword", NEWPASSWORD);
        formTester.setValue("newPasswordConfirmFormGroup:newPasswordConfirmFormGroup_body:newPasswordConfirm", NEWPASSWORD);
        formTester.submit();

        String currentNewPassword = getSession().getUser().getPassword();
        Assert.assertTrue(authService.checkPassword(NEWPASSWORD, currentNewPassword));

        wicketTester.assertInfoMessages("Sie haben ihr Passwort erfolgreich geändert!");
        wicketTester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    @DirtiesContext
    public void newPasswordsAreNotEqual() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);

        FormTester formTester = wicketTester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordFormGroup:currentPasswordFormGroup_body:currentPassword", TestFactory.USER_PASSWORD);

        formTester.setValue("newPasswordFormGroup:newPasswordFormGroup_body:newPassword", NEWPASSWORD);
        formTester.setValue("newPasswordConfirmFormGroup:newPasswordConfirmFormGroup_body:newPasswordConfirm", NEWPASSWORD + "dumbass");
        formTester.submit();

        String currentNewPassword = getSession().getUser().getPassword();
        Assert.assertFalse(authService.checkPassword(NEWPASSWORD, currentNewPassword));

        wicketTester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    @DirtiesContext
    public void changeEmail() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);

        FormTester formTester = wicketTester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordFormGroup:currentPasswordFormGroup_body:currentPassword", TestFactory.USER_PASSWORD);

        formTester.setValue("newEmailFormGroup:newEmailFormGroup_body:newEmail", NEWEMAIL);
        formTester.setValue("newEmailConfirmFormGroup:newEmailConfirmFormGroup_body:newEmailConfirm", NEWEMAIL);
        formTester.submit();

        String currentNewEmail = getSession().getUser().getEmail();
        Assert.assertTrue(currentNewEmail.equals(NEWEMAIL));

        wicketTester.assertInfoMessages("Sie haben ihre E-Mail-Adresse erfolgreich geändert!");
        wicketTester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    @DirtiesContext
    public void newEmailsAreNotEqual() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);

        FormTester formTester = wicketTester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordFormGroup:currentPasswordFormGroup_body:currentPassword", TestFactory.USER_PASSWORD);

        formTester.setValue("newEmailFormGroup:newEmailFormGroup_body:newEmail", NEWEMAIL);
        formTester.setValue("newEmailConfirmFormGroup:newEmailConfirmFormGroup_body:newEmailConfirm", NEWEMAIL + "imakeyoubullshit");
        formTester.submit();

        String currentNewEmail = getSession().getUser().getEmail();
        Assert.assertFalse(currentNewEmail.equals(NEWEMAIL));

        wicketTester.assertRenderedPage(EditPasswordPage.class);
    }

    @Test
    @DirtiesContext
    public void newEmailsAreInvalidEmailAddresses() throws Exception {
        wicketTester.startPage(EditPasswordPage.class);

        FormTester formTester = wicketTester.newFormTester("editPassword");

        // Set the current password
        formTester.setValue("currentPasswordFormGroup:currentPasswordFormGroup_body:currentPassword", TestFactory.USER_PASSWORD);

        formTester.setValue("newEmailFormGroup:newEmailFormGroup_body:newEmail", NEWEMAIL_INVALID);
        formTester.setValue("newEmailConfirmFormGroup:newEmailConfirmFormGroup_body:newEmailConfirm", NEWEMAIL_INVALID);
        formTester.submit();

        String currentNewEmail = getSession().getUser().getEmail();
        Assert.assertFalse(currentNewEmail.equals(NEWEMAIL));

        wicketTester.assertRenderedPage(EditPasswordPage.class);
    }
}
