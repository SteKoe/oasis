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
import org.thymeleaf.expression.Messages;

import java.util.Locale;

@Controller
public class IndexController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/")
    public String index(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              RedirectAttributes redirectAttributes,
                              Locale locale) {

        ModelAndView model = new ModelAndView();
        model.addObject("pageTitle", messageSource.getMessage("form.login.title", null, locale));

        if (error != null) {
            model.addObject("flashError", messageSource.getMessage("message.login.error", null, locale));
        }

        if (logout != null) {
            model.addObject("flashSuccess", messageSource.getMessage("message.logout.success", null, locale));
        }
        model.setViewName("auth/login");

        return model;

    }
}