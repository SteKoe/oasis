package de.stekoe.oasis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Locale;

@Controller
public class IndexController {

    @Autowired
    private MessageSource messageSource;

    @Autowired

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              RedirectAttributes redirectAttributes,
                              Locale locale) {

        ModelAndView model = new ModelAndView();

        if (error != null) {
            model.addObject("flashError", messageSource.getMessage("message.login.error", null, locale));
        }

        if (logout != null) {
            model.addObject("flashSuccess", messageSource.getMessage("message.logout.success", null, locale));
        }
        model.setViewName("auth/login");

        return model;

    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {
        ModelAndView model = new ModelAndView("/error/403");

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.addObject("pageTitle", "Zugriff verweigert!");
        return model;

    }
}