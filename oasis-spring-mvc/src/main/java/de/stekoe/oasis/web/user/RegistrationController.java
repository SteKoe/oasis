package de.stekoe.oasis.web.user;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.*;
import de.stekoe.oasis.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class RegistrationController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    AuthService authService;

    @Autowired
    SystemRoleService systemRoleService;

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerUser(Model model, Locale locale, HttpServletRequest request) {
        model.addAttribute("pageTitle", messageSource.getMessage("label.register", null, locale));
        model.addAttribute("userRegistrationForm", new UserRegistrationForm());
        return "/user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processSubmit(@Valid UserRegistrationForm userRegistrationForm, BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        User user = null;
        Company company = null;

        ModelAndView model = new ModelAndView("/user/register");
        model.addObject("pageTitle", messageSource.getMessage("label.register", null, locale));

        if(bindingResult.hasErrors()) {
            model.addObject("flashError", messageSource.getMessage("message.form.fix.errors", null, locale));
            return model;
        } else {
            try {
                user = new User();
                user.setUsername(userRegistrationForm.getEmail());
                user.setEmail(userRegistrationForm.getEmail());
                user.setPassword(authService.hashPassword(userRegistrationForm.getPassword()));
                user.getRoles().add(systemRoleService.getUserRole());
                userService.save(user);

                if(userRegistrationForm.getRegistryToken().trim().isEmpty() == false) {
                    company = companyService.findByRegistrationToken(userRegistrationForm.getRegistryToken());

                    if(company != null) {
                        Employee employee = new Employee();
                        employee.setUser(user);
                        employee.setEmployeeStatus(UserStatus.ACTIVATION_PENDING);
                        company.getEmployees().add(employee);

                        companyService.save(company);
                    }
                } else if(userRegistrationForm.getCompany().getName().trim().isEmpty() == false) {
                    company = new Company(userRegistrationForm.getCompany());
                    company.getAddresses().add(userRegistrationForm.getCompany().getAddress());
                    companyService.save(company);

                    CompanyRole companyRole = new CompanyRole();
                    companyRole.setName("Administrator");
                    companyRole.getPermissions().add(new Permission(PermissionObject.COMPANY, PermissionType.ALL, company.getId()));
                    company.getRoles().add(companyRole);

                    Employee employee = new Employee();
                    employee.setUser(user);
                    employee.setRole(companyRole);
                    employee.setEmployeeStatus(UserStatus.ACTIVATED);
                    company.getEmployees().add(employee);

                    companyService.save(company);
                }

                if(company == null) {
                    model.addObject("flashError", messageSource.getMessage("message.company.not.found", null, locale));
                    userService.delete(user);
                    return model;
                }
            } catch (EmailAddressAlreadyInUseException e) {
                model.addObject("flashError", messageSource.getMessage("message.email.already.in.use", null, locale));
                return model;
            } catch (UsernameAlreadyInUseException e) {
                model.addObject("flashError", messageSource.getMessage("message.username.already.in.use", null, locale));
                return model;
            } catch (Exception e) {
                if(user != null) {
                    userService.delete(user);
                }
                if(company != null) {
                    companyService.delete(company);
                }
                model.addObject("flashError", messageSource.getMessage("message.registration.error", null, locale));
                return model;
            }
        }

        model.setViewName("redirect:/login");

        try {
            mailService.sendRegistrationMail(user, request, locale);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("flashSuccess", messageSource.getMessage("message.registration.success", null, locale));
        return model;
    }

    @RequestMapping(value = "/activate/{activationToken}")
    public String activateUser(@PathVariable String activationToken, RedirectAttributes redirectAttributes, Locale locale) {
        User user = userService.findByActivationCode(activationToken);
        if(user == null) {
            redirectAttributes.addFlashAttribute("flashError", messageSource.getMessage("message.user.activation.error", null, locale));
        } else {
            try {
                user.activate();
                userService.save(user);
                redirectAttributes.addFlashAttribute("flashSuccess", messageSource.getMessage("message.user.activation.success", null, locale));
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("flashError", messageSource.getMessage("message.user.activation.error", null, locale));
            }
        }

        return "redirect:/";
    }
}
