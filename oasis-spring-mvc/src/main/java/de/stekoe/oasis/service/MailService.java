package de.stekoe.oasis.service;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    ServletContext servletContext;

    @Autowired
    MessageSource messageSource;

    @Value("${mail.test.to}")
    String testReceipient;

    @Value("${mail.from}")
    String from;

    @Value("${baseUrl}")
    String baseUrl;

    public void sendRegistrationMail(final User user, HttpServletRequest request, final Locale locale) throws Exception {
        WebContext ctx = new WebContext(request, null, servletContext, locale);
        ctx.setVariable("username", user.getEmail());
        ctx.setVariable("token", user.getActivationKey());
        ctx.setVariable("baseUrl", baseUrl);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject(messageSource.getMessage("mail.registration.subject", null, locale));
        message.setFrom(from);
        message.setTo(user.getEmail());

        final String htmlContent = templateEngine.process("registration", ctx);
        message.setText(htmlContent, true); // true = isHtml

        this.mailSender.send(mimeMessage);
    }

    public void sendCompanyInviteMail(User invitingUser, User invitedUser, String plainPassword, Company company, HttpServletRequest request, final Locale locale) throws MessagingException {
        WebContext ctx = new WebContext(request, null, servletContext, locale);
        ctx.setVariable("invitingUser", invitingUser.getEmail());
        ctx.setVariable("company", company);
        ctx.setVariable("invitedUser", invitedUser.getEmail());
        ctx.setVariable("password", plainPassword);
        ctx.setVariable("token", invitedUser.getActivationKey());
        ctx.setVariable("baseUrl", baseUrl);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(messageSource.getMessage("mail.registration.subject", null, locale));
        message.setFrom(from);
        message.setTo(invitedUser.getEmail());

        final String htmlContent = templateEngine.process("company_invite", ctx);
        message.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);
    }

    public void sendTestMail() throws Exception {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject("Example HTML email with inline image");
        message.setFrom(from);
        message.setTo(testReceipient);
        message.setText("Testmail from your OASIS installation!", false);
        this.mailSender.send(mimeMessage);
    }

    public void sendResetPasswordMail(User user, HttpServletRequest request, Locale locale) throws Exception {
        WebContext ctx = new WebContext(request, null, servletContext, locale);
        ctx.setVariable("username", user.getEmail());
        ctx.setVariable("token", user.getActivationKey());
        ctx.setVariable("baseUrl", baseUrl);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject(messageSource.getMessage("mail.reset.password.subject", Arrays.asList(user.getEmail()).toArray(), locale));
        message.setFrom(from);
        message.setTo(user.getEmail());

        final String htmlContent = templateEngine.process("resetPassword", ctx);
        message.setText(htmlContent, true); // true = isHtml

        this.mailSender.send(mimeMessage);
    }
}
