package de.stekoe.oasis.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConcatController {

    @RequestMapping("/contact")
    public ModelAndView contact( ) {
        ModelAndView model = new ModelAndView();
        model.setViewName("contact");
        return model;
    }
}
