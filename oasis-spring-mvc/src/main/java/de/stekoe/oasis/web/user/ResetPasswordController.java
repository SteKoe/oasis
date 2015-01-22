package de.stekoe.oasis.web.user;

import de.stekoe.oasis.model.User;
import de.stekoe.oasis.model.UserStatus;
import de.stekoe.oasis.service.AuthService;
import de.stekoe.oasis.service.UserService;
import de.stekoe.oasis.service.MailService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.UUID;

@Controller
public class ResetPasswordController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    MailService mailService;

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(@RequestParam(required = false) String email, RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/user/requestPassword");

        if(StringUtils.isBlank(email) == false) {
            User user = userService.findByEmail(email);
            if(user != null) {
                String pwRequestToken = DigestUtils.md5Hex(UUID.randomUUID().toString());
                user.setActivationKey(pwRequestToken);
                user.setUserStatus(UserStatus.RESET_PASSWORD);
                try {
                    userService.save(user);
                    mailService.sendResetPasswordMail(user, request, locale);
                } catch (Exception e) {
                    // NOP, as we dont care: This prevents email spoofing!
                    // We always print a success message
                }

                redirectAttributes.addFlashAttribute("flashSuccess", messageSource.getMessage("message.password.reset.email", null, locale));
                model.setViewName("redirect:/login");
            }
        }

        return model;
    }

    @RequestMapping(value = "/resetPassword/{passwordResetToken}", method = RequestMethod.GET)
    public ModelAndView resetPasswordToken(@PathVariable String passwordResetToken, RedirectAttributes redirectAttributes, Locale locale) {
        User user = userService.findByPasswordResetToken(passwordResetToken);

        ModelAndView model = new ModelAndView("/user/resetPassword");

        if(user == null) {
            redirectAttributes.addFlashAttribute("flashError", messageSource.getMessage("message.password.reset.error", null, locale));
            model.setViewName("redirect:/login");
            return model;
        } else {
            model.addObject("resetPasswordForm", new ResetPasswordForm());
            model.addObject("resetPasswordToken", passwordResetToken);
            return model;
        }
    }

    @RequestMapping(value = "/resetPassword/{passwordResetToken}", method = RequestMethod.POST)
    public ModelAndView test(@PathVariable String passwordResetToken, @Valid ResetPasswordForm resetPasswordForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Locale locale) {
        User user = userService.findByPasswordResetToken(passwordResetToken);

        ModelAndView model = new ModelAndView("/user/resetPassword");

        if(bindingResult.hasErrors() == false) {
            String hashedPassword = authService.hashPassword(resetPasswordForm.getPassword());
            user.setPassword(hashedPassword);
            user.setUserStatus(UserStatus.ACTIVATED);
            user.setActivationKey(null);
            try {
                userService.save(user);
            } catch (Exception e) {
            }

            redirectAttributes.addAttribute("flashSuccess", messageSource.getMessage("message.password.reset.success", null, locale));
            model.setViewName("redirect:/login");
        } else {
            model.addObject("resetPasswordToken", passwordResetToken);
        }
        return model;
    }
}
