package de.stekoe.oasis.web.user;

import de.bripkens.gravatar.Gravatar;
import de.stekoe.oasis.model.Company;
import de.stekoe.oasis.model.Employee;
import de.stekoe.oasis.model.User;
import de.stekoe.oasis.model.UserProfile;
import de.stekoe.oasis.service.CompanyService;
import de.stekoe.oasis.service.EmailAddressAlreadyInUseException;
import de.stekoe.oasis.service.UserService;
import de.stekoe.oasis.service.UsernameAlreadyInUseException;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/user/profile/edit", method = RequestMethod.GET)
    public ModelAndView edit(Locale locale) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);

        ModelAndView model = new ModelAndView("/user/profile.edit");
        model.addObject("pageTitle", messageSource.getMessage("label.user.profile.edit", null, locale));
        model.addObject("userProfile", user.getProfile());
        return model;
    }

    @RequestMapping(value = "/user/profile/edit", method = RequestMethod.POST)
    public String editPost(UserProfile userProfile) throws EmailAddressAlreadyInUseException, UsernameAlreadyInUseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);

        UserProfile profile = user.getProfile();
        profile.setNameSuffix(userProfile.getNameSuffix());
        profile.setFirstname(userProfile.getFirstname());
        profile.setSurname(userProfile.getSurname());
        profile.setTelefon(userProfile.getTelefon());
        profile.setTelefax(userProfile.getTelefax());
        profile.setAddress(userProfile.getAddress());

        userService.save(user);
        return "redirect:/user/profile";
    }


    @RequestMapping(value = {"/user/profile", "/user/profile/{username:.+}"}, method = RequestMethod.GET)
    public String view(Model model, HttpServletRequest request) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("pageTitle", "Das ist Ihr Profil!");
        model.addAttribute("currentUsersProfile", true);

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if(pathVariables != null) {
            if(pathVariables.containsKey("username")) {
                String reqUsername = (String)pathVariables.get("username");
                String s = StringUtils.newStringUtf8(reqUsername.getBytes("UTF-8"));
                if(!reqUsername.equals(username)) {
                    model.addAttribute("pageTitle", "Profil: " + reqUsername);
                    model.addAttribute("currentUsersProfile", false);
                    username = reqUsername;
                }
            }
        }

        User user = userService.findByUsername(username);
        Company company = companyService.findByUser(user.getId());
        Employee employee = null;
        if(company != null) {
            employee = company.getEmployees().stream().filter(emp -> emp.getUser().getId().equals(user.getId())).findFirst().get();
        }

        model.addAttribute("image", getGravatar(user));
        model.addAttribute("user", user);
        model.addAttribute("company", company);
        model.addAttribute("employee", employee);

        return "/user/profile.view";
    }

    private String getGravatar(User user) {
        if (user.getEmail() == null) {
            return null;
        } else {
            Gravatar g = new Gravatar();
            return g.getUrl(user.getEmail());
        }
    }

}
