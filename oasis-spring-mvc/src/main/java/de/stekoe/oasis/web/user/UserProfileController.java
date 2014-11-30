package de.stekoe.oasis.web.user;

import de.bripkens.gravatar.Gravatar;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    User user;

    @RequestMapping("/user/profile/edit")
    public String edit() {
        return "user/profile.edit";
    }


    @RequestMapping(value = {"/user/profile", "/user/profile/{username:.+}"})
    public String view(Model model, HttpServletRequest request) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("pageTitle", "Das ist Ihr Profil!");

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if(pathVariables != null) {
            if(pathVariables.containsKey("username")) {
                String reqUsername = (String)pathVariables.get("username");
                reqUsername = URLDecoder.decode(reqUsername, "UTF-8");
                if(!reqUsername.equals(username)) {
                    model.addAttribute("pageTitle", "Profil: " + reqUsername);
                    username = reqUsername;
                }
            }
        }

        user = userService.findByUsername(username);

        model.addAttribute("user", user);

        return "user/profile.view";
    }

    private String getGravatar() {
        if (user.getEmail() == null) {
            return null;
        } else {
            Gravatar g = new Gravatar();
            return g.getUrl(user.getEmail());
        }
    }

}
